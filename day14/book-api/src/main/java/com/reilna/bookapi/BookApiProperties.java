package com.reilna.bookapi;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "book-api")
public class BookApiProperties {

    @Min(1)
    private int maxAuthorLength;

    public int getMaxAuthorLength() {
        return maxAuthorLength;
    }

    public void setMaxAuthorLength(int maxAuthorLength) {
        this.maxAuthorLength = maxAuthorLength;
    }
}
