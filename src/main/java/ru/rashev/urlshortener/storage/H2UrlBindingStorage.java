package ru.rashev.urlshortener.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rashev.urlshortener.ShortUrlComponents;
import ru.rashev.urlshortener.service.Partitioner;

import java.util.List;
import java.util.Optional;

/**
 * @author konstantin-rashev on 01/09/2019.
 */
@Component
public class H2UrlBindingStorage implements UrlBindingStorage {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Partitioner partitioner;

    public H2UrlBindingStorage(JdbcTemplate jdbcTemplate, Partitioner partitioner) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.partitioner = partitioner;
    }

    @Override
    @Transactional
    public void storeBinding(String originalUrl, ShortUrlComponents shortUrlComponents) {
        int partition = partitioner.partitionForOriginalUrl(originalUrl);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("shortUrlId", shortUrlComponents.getId())
                .addValue("shortUrlDomain", shortUrlComponents.getDomain())
                .addValue("partition", partition)
                .addValue("originalUrl", originalUrl);
        namedParameterJdbcTemplate.update("INSERT INTO url_binding (short_url_id, short_url_domain, original_url, partition) " +
                "VALUES (:shortUrlId, :shortUrlDomain, :originalUrl, :partition)", params);
    }

    @Override
    public Optional<ShortUrlComponents> getShortUrlComponentsBy(String originalUrl) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("originalUrl", originalUrl);
        List<ShortUrlComponents> shortUrlComponentsList = namedParameterJdbcTemplate.query(
                "SELECT short_url_id, short_url_domain FROM url_binding WHERE original_url = :originalUrl LIMIT 1",
                params,
                (rs, rowNum) -> new ShortUrlComponents(
                        rs.getString("short_url_id"),
                        rs.getString("short_url_domain")
                ));
        if (shortUrlComponentsList.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(shortUrlComponentsList.get(0));
        }
    }

    @Override
    public Optional<String> resolveShortUrl(ShortUrlComponents shortUrlComponents) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("shortUrlId", shortUrlComponents.getId())
                .addValue("shortUrlDomain", shortUrlComponents.getDomain());
        List<String> originalUrlList = namedParameterJdbcTemplate.query(
                "SELECT original_url FROM url_binding WHERE short_url_id = :shortUrlId AND short_url_domain = :shortUrlDomain LIMIT 1",
                params,
                (rs, rowNum) -> rs.getString("original_url")
        );
        if (originalUrlList.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(originalUrlList.get(0));
        }
    }
}
