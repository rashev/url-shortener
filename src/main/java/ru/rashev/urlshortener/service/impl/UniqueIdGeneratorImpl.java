package ru.rashev.urlshortener.service.impl;

import io.seruco.encoding.base62.Base62;
import org.springframework.stereotype.Component;
import ru.rashev.urlshortener.config.ApplicationConfig;
import ru.rashev.urlshortener.service.SequenceGenerator;
import ru.rashev.urlshortener.service.UniqueIdGenerator;

import java.nio.charset.StandardCharsets;

/**
 * @author konstantin-rashev on 28/08/2019.
 */
@Component
public class UniqueIdGeneratorImpl implements UniqueIdGenerator {

    private final Base62 base62 = Base62.createInstance();
    private final SequenceGenerator sequenceGenerator;
    private final byte serviceInstanceId;

    public UniqueIdGeneratorImpl(SequenceGenerator sequenceGenerator,
                                 ApplicationConfig config) {
        this.sequenceGenerator = sequenceGenerator;
        this.serviceInstanceId = config.getServiceInstanceId();
    }

    @Override
    public String generate(int partition) {
        String uniqueIdOrigin = partition + "-" +
                serviceInstanceId + "-" +
                sequenceGenerator.generateNext() + "-" +
                String.format("%1$05d", System.nanoTime() & 0xFFFFL);
        return new String(base62.encode(uniqueIdOrigin.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    @Override
    public int extractPartition(String id) {
        String idOrigin = new String(base62.decode(id.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        String partitionStr = idOrigin.substring(0, idOrigin.indexOf("-"));
        return Integer.parseInt(partitionStr);
    }
}
