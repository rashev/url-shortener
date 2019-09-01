package ru.rashev.urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.rashev.urlshortener.config.ApplicationConfig;
import ru.rashev.urlshortener.dto.OriginalUrlRequest;
import ru.rashev.urlshortener.dto.OriginalUrlResponse;
import ru.rashev.urlshortener.dto.ShorteningRequest;
import ru.rashev.urlshortener.dto.ShorteningResponse;
import ru.rashev.urlshortener.service.ShorteningService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author konstantin-rashev on 27/08/2019.
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final ApplicationConfig config;
    private final ShorteningService shorteningService;

    public ApiController(ApplicationConfig config, ShorteningService shorteningService) {
        this.config = config;
        this.shorteningService = shorteningService;
    }

    @ResponseBody
    @RequestMapping(value = "shortenUrl", method = RequestMethod.POST)
    public ShorteningResponse shortenUrl(@Valid @RequestBody ShorteningRequest request) {
        try {
            String shortUrlDomain = request.getDomain() != null ? request.getDomain() : config.getDefaultDomain();
            return new ShorteningResponse(shorteningService.buildShortUrlFor(request.getOriginalUrl(), shortUrlDomain));
        } catch (Exception ex) {
            log.error("Unexpected error during execution shortenUrl request", ex);
            throw new RuntimeException("Unable to process request due technical error");
        }
    }

    @ResponseBody
    @RequestMapping(value = "originalUrl", method = RequestMethod.POST)
    public OriginalUrlResponse getOriginalUrl(@Valid @RequestBody OriginalUrlRequest request) {
        try {
            Optional<String> optionalOriginalUrl = shorteningService.getOriginalUrlBy(request.getShortUrlId(),
                    request.getShortUrlDomain());
            return optionalOriginalUrl
                    .map(s -> new OriginalUrlResponse(s, true))
                    .orElseGet(() -> new OriginalUrlResponse(null, false));
        } catch (Exception ex) {
            log.error("Unexpected error during execution originalUrl request", ex);
            throw new RuntimeException("Unable to process request due technical error");
        }
    }
}
