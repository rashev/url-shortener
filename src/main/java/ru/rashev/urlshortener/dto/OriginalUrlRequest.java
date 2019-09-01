package ru.rashev.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
public class OriginalUrlRequest {
    @NotBlank
    private final String shortUrlId;
    @NotBlank
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
