package ru.rashev.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
public class OriginalUrlResponse {
    private final String originalUrl;
    private final boolean exists;

    public OriginalUrlResponse(String originalUrl, boolean exists) {
        this.originalUrl = originalUrl;
        this.exists = exists;
    }

    @JsonProperty("originalUrl")
    public String getOriginalUrl() {
        return originalUrl;
    }

    @JsonProperty("exists")
    public boolean isExists() {
        return exists;
    }
}
