package ru.rashev.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
@JsonInclude(NON_NULL)
public class OriginalUrlResponse {
    private final String originalUrl;
    private final boolean exists;

    public OriginalUrlResponse(@JsonProperty("originalUrl") String originalUrl,
                               @JsonProperty("exists") boolean exists) {
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
