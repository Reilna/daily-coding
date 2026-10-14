package com.reilna.bookapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void shouldReturnBooks() throws Exception{
        when(bookService.findAll("Все"))
                .thenReturn(List.of(
                        new Book(1L, "Java", "Иван")
                ));
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java"));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRejectBlankBook() throws Exception{
        CreateBookRequest request = new CreateBookRequest("", "");
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Название не должно быть пустым"));
    }

    @Test
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
    void shouldDeleteBookSuccessfully() throws Exception{

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteById(1L);
    }

    @Test
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
}
