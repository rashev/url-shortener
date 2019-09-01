package ru.rashev.urlshortener.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rashev.urlshortener.service.SequenceGenerator;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
@Component
public class SequenceGeneratorImpl implements SequenceGenerator {

    private static final Logger log = LoggerFactory.getLogger(SequenceGeneratorImpl.class);

    private final AtomicLong localCounter = new AtomicLong(0);
    private volatile long reservedUpperBound = 0;
    private final JdbcTemplate jdbcTemplate;

    public SequenceGeneratorImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        Long currVal = jdbcTemplate.queryForObject("select currval('counter')", Long.TYPE);
        if (currVal == null) {
            throw new IllegalStateException("Unexpected counter value");
        }
        log.info("Read initial value [{}]", currVal);
        return currVal;
    }

    private long reserveNextBatch() {
        Long nextVal = jdbcTemplate.queryForObject("select nextval('counter')", Long.TYPE);
        if (nextVal == null) {
            throw new IllegalStateException("Unexpected counter next value");
        }
        log.info("Reserved counter values up to [{}]", nextVal);
        return nextVal;
    }
}
