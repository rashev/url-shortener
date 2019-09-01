package ru.rashev.urlshortener.service.impl;

import org.springframework.stereotype.Component;
import ru.rashev.urlshortener.service.Partitioner;
import ru.rashev.urlshortener.service.UniqueIdGenerator;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
@Component
public class PartitionerImpl implements Partitioner {

    private static final int PARTITIONS_COUNT = 256;

    private final UniqueIdGenerator uniqueIdGenerator;

    public PartitionerImpl(UniqueIdGenerator uniqueIdGenerator) {
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    @Override
    public int partitionForOriginalUrl(String originalUrl) {
        return Math.abs(originalUrl.hashCode() % PARTITIONS_COUNT);
    }

    @Override
    public int partitionForShortUrlId(String shortUrlId) {
        return uniqueIdGenerator.extractPartition(shortUrlId);
    }
}
