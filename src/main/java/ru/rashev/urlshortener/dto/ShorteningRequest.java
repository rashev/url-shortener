package ru.rashev.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author konstantin-rashev on 27/08/2019.
 */
public class ShorteningRequest {
    @NotBlank
    private final String originalUrl;
    private final String domain;

    public ShorteningRequest(@JsonProperty("originalUrl") String originalUrl,
                             @JsonProperty("domain") String domain) {
        this.originalUrl = originalUrl;
        this.domain = domain;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getDomain() {
        return domain;
    }
}
