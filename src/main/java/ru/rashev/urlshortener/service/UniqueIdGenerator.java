package ru.rashev.urlshortener.service;

/**
 * @author konstantin-rashev on 28/08/2019.
 */
public interface UniqueIdGenerator {
    String generate(int partition);

    int extractPartition(String id);
}
