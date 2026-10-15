package com.reilna.bookapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "book-api")
public class BookApiProperties {

    private int maxAuthorLength;

    public int getMaxAuthorLength() {
        return maxAuthorLength;
    }

    public void setMaxAuthorLength(int maxAuthorLength) {
        this.maxAuthorLength = maxAuthorLength;
    }
}
