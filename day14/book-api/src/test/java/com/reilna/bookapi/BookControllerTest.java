package com.reilna.bookapi;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void shouldReturnBooks() throws Exception{
        when(bookService.findAll(eq("Все"), eq(""), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(new Book(1L, "Java", "Иван"))
                ));
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Java"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldRejectBlankBook() throws Exception{
        CreateBookRequest request = new CreateBookRequest("", "");
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Название не должно быть пустым"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldCreateBook() throws Exception{
        CreateBookRequest request = new CreateBookRequest("Java", "Иван");
        when(bookService.create("Java", "Иван"))
                .thenReturn(new Book(1L, "Java", "Иван"));

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenBookNotFound() throws Exception{
        when(bookService.findById(999L))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Книга не найдена"
                ));

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Книга не найдена"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldDeleteBookSuccessfully() throws Exception{

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldUpdateBook() throws Exception{
        CreateBookRequest request = new CreateBookRequest("Java: новое издание", "Иван");

        when(bookService.update(1L, request.title(), request.author()))
                .thenReturn(new Book(1L, request.title(), request.author()));

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java: новое издание"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldLimitPageSizeTo100() throws Exception {
        when(bookService.findAll(eq("Все"), eq(""), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/books")
                .param("size", "1000"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor =
                ArgumentCaptor.forClass(Pageable.class);

        verify(bookService).findAll(eq("Все"), eq(""), pageableCaptor.capture());

        assertEquals(100, pageableCaptor.getValue().getPageSize());
    }

    @Test
    void shouldPassFiltersPaginationAndSortingToService() throws Exception {
        when(bookService.findAll(
                eq("ив"),
                eq("java"),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/books")
                .param("author", "ив")
                .param("title", "java")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "title,asc"));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(bookService).findAll(
                eq("ив"),
                eq("java"),
                pageableCaptor.capture()
        );

        Pageable pageable = pageableCaptor.getValue();

        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertEquals(
                Sort.by(Sort.Direction.ASC, "title"),
                pageable.getSort()
        );
    }
}
