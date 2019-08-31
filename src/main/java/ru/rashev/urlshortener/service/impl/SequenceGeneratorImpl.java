package ru.rashev.urlshortener.service.impl;

import org.springframework.stereotype.Component;
import ru.rashev.urlshortener.config.ApplicationConfig;
import ru.rashev.urlshortener.service.SequenceGenerator;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
@Component
public class SequenceGeneratorImpl implements SequenceGenerator {

    private final AtomicLong localCounter = new AtomicLong(0);
    private final int reservationSize;
    private volatile long reservedUpperBound = 0;

    public SequenceGeneratorImpl(ApplicationConfig config) {
        reservationSize = config.getCounterReservationSize();
    }

    @PostConstruct
    public void init() {
        localCounter.set(readInitialValue());
        reservedUpperBound = reserveNextBatch();
    }

    @Override
    public long generateNext() {
        long currentVal = localCounter.incrementAndGet();
        while (currentVal >= reservedUpperBound) {
            synchronized (this) {
                if (currentVal >= reservedUpperBound) {
                    reservedUpperBound = reserveNextBatch();
                }
            }
        }
        return currentVal;
    }

    private long readInitialValue() {
        return 0; //TODO read from storage
    }

    private long reserveNextBatch() {
        return reservedUpperBound + reservationSize;//TODO read from storage
    }
}
