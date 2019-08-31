package ru.rashev.urlshortener.storage;

import ru.rashev.urlshortener.ShortUrlComponents;

import java.util.Optional;

/**
 * @author konstantin-rashev on 28/08/2019.
 */
public interface UrlBindingStorage {

    void storeBinding(String originalUrl, ShortUrlComponents shortUrlComponents);

    Optional<ShortUrlComponents> getShortUrlComponentsBy(String originalUrl);

    Optional<String> resolveShortUrl(ShortUrlComponents shortUrlComponents);

}
