package ru.rashev.urlshortener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
@Configuration
@ConfigurationProperties(prefix = "shortener")
public class ApplicationConfig {

    private String defaultDomain;

    private String shortUrlPort;

    private String shortUrlScheme;

    private byte serviceInstanceId;

    public String getDefaultDomain() {
        return defaultDomain;
    }

    public void setDefaultDomain(String defaultDomain) {
        this.defaultDomain = defaultDomain;
    }

    public String getShortUrlPort() {
        return shortUrlPort;
    }

    public void setShortUrlPort(String shortUrlPort) {
        this.shortUrlPort = shortUrlPort;
    }

    public String getShortUrlScheme() {
        return shortUrlScheme;
    }

    public void setShortUrlScheme(String shortUrlScheme) {
        this.shortUrlScheme = shortUrlScheme;
    }

    public byte getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(byte serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }
}
