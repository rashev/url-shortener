package ru.rashev.urlshortener.storage;

import org.springframework.stereotype.Component;
import ru.rashev.urlshortener.ShortUrlComponents;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author konstantin-rashev on 28/08/2019.
 */
@Component
public class InMemoryUrlBindingStorage implements UrlBindingStorage {

    private final Map<ShortUrlComponents, Binding> shortUrlComponentsToBinding = new ConcurrentHashMap<>();
    private final Map<String, Binding> originalUrlToBinding = new ConcurrentHashMap<>();

    @Override
    public void storeBinding(String originalUrl, ShortUrlComponents shortUrlComponents) {
        Binding binding = new Binding(originalUrl, shortUrlComponents);//TODO prevent races
        shortUrlComponentsToBinding.put(shortUrlComponents, binding);
        originalUrlToBinding.put(originalUrl, binding);
    }

    @Override
    public Optional<ShortUrlComponents> getShortUrlComponentsBy(String originalUrl) {
        return Optional.ofNullable(originalUrlToBinding.get(originalUrl)).map(it -> it.shortUrlComponents);
    }

    @Override
    public Optional<String> resolveShortUrl(ShortUrlComponents shortUrlComponents) {
        return Optional.ofNullable(shortUrlComponentsToBinding.get(shortUrlComponents)).map(it -> it.originalUrl);
    }

    private static class Binding {
        final String originalUrl;
        final ShortUrlComponents shortUrlComponents;

        Binding(String originalUrl, ShortUrlComponents shortUrlComponents) {
            this.originalUrl = originalUrl;
            this.shortUrlComponents = shortUrlComponents;
        }
    }
}
