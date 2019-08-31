package ru.rashev.urlshortener.service;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
public interface Partitioner {
    int partitionForOriginalUrl(String originalUrl);

    int partitionForShortUrlId(String shortUrlId);
}
