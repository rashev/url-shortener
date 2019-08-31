package ru.rashev.urlshortener.service;

import java.util.Optional;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
public interface ShorteningService {
    String buildShortUrlFor(String originalUrl, String shortUrlDomain);

    Optional<String> getOriginalUrlBy(String shortUrlId, String shortUrlDomain);
}
