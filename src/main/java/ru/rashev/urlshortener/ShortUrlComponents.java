package ru.rashev.urlshortener;

import java.util.Objects;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
public class ShortUrlComponents {
    private final String id;
    private final String domain;

    public ShortUrlComponents(String id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrlComponents that = (ShortUrlComponents) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(domain, that.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, domain);
    }

    @Override
    public String toString() {
        return "ShortUrlComponents{" +
                "id='" + id + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
