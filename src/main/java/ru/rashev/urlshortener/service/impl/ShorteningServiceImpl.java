package ru.rashev.urlshortener.service.impl;

import org.springframework.stereotype.Component;
import ru.rashev.urlshortener.ShortUrlComponents;
import ru.rashev.urlshortener.config.ApplicationConfig;
import ru.rashev.urlshortener.service.Partitioner;
import ru.rashev.urlshortener.service.ShorteningService;
import ru.rashev.urlshortener.service.UniqueIdGenerator;
import ru.rashev.urlshortener.storage.UrlBindingStorage;

import java.util.Optional;

/**
 * @author konstantin-rashev on 28/08/2019.
 */
@Component
public class ShorteningServiceImpl implements ShorteningService {

    private final String shortUrlPortSuffix;
    private final String shortUrlScheme;

    private final UniqueIdGenerator uniqueIdGenerator;
    private final UrlBindingStorage bindingStorage;
    private final Partitioner partitioner;

    public ShorteningServiceImpl(UniqueIdGenerator uniqueIdGenerator, UrlBindingStorage bindingStorage,
                                 Partitioner partitioner, ApplicationConfig config) {
        this.uniqueIdGenerator = uniqueIdGenerator;
        this.bindingStorage = bindingStorage;
        this.partitioner = partitioner;
        this.shortUrlPortSuffix = config.getShortUrlPort() != null && !config.getShortUrlPort().isBlank() ?
                ":" + config.getShortUrlPort() : "";
        this.shortUrlScheme = config.getShortUrlScheme();
    }

    @Override
    public String buildShortUrlFor(String originalUrl, String shortUrlDomain) {
        Optional<ShortUrlComponents> optionalShortUrl = bindingStorage.getShortUrlComponentsBy(originalUrl);
        if (optionalShortUrl.isEmpty()) {
            int partition = partitioner.partitionForOriginalUrl(originalUrl);
            String uniqueId = uniqueIdGenerator.generate(partition);
            ShortUrlComponents shortUrlComponents = new ShortUrlComponents(uniqueId, shortUrlDomain);
            bindingStorage.storeBinding(originalUrl, shortUrlComponents);
            return buildResultUrl(shortUrlDomain, uniqueId);
        } else {
            ShortUrlComponents shortUrlComponents = optionalShortUrl.get();
            return buildResultUrl(shortUrlComponents.getDomain(), shortUrlComponents.getId());
        }
    }

    private String buildResultUrl(String shortUrlDomain, String uniqueId) {
        return shortUrlScheme + "://" + shortUrlDomain + shortUrlPortSuffix + "/" + uniqueId;
    }

    @Override
    public Optional<String> getOriginalUrlBy(String shortUrlId, String shortUrlDomain) {
        return bindingStorage.resolveShortUrl(new ShortUrlComponents(shortUrlId, shortUrlDomain));
    }
}
