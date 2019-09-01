package ru.rashev.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author konstantin-rashev on 27/08/2019.
 */
public class ShorteningResponse {
    private final String shortUrl;

    public ShorteningResponse(@JsonProperty("shortUrl") String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @JsonProperty("shortUrl")
    public String getShortUrl() {
        return shortUrl;
    }
}
