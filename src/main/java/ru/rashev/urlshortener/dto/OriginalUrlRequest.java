package ru.rashev.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
public class OriginalUrlRequest {
    private final String shortUrlId;
    private final String shortUrlDomain;

    public OriginalUrlRequest(@JsonProperty("shortUrlId") String shortUrlId,
                              @JsonProperty("shortUrlDomain") String shortUrlDomain) {
        this.shortUrlId = shortUrlId;
        this.shortUrlDomain = shortUrlDomain;
    }

    public String getShortUrlId() {
        return shortUrlId;
    }

    public String getShortUrlDomain() {
        return shortUrlDomain;
    }
}
