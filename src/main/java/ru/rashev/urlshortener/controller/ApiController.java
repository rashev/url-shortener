package ru.rashev.urlshortener.controller;

import org.springframework.web.bind.annotation.*;
import ru.rashev.urlshortener.config.ApplicationConfig;
import ru.rashev.urlshortener.dto.OriginalUrlRequest;
import ru.rashev.urlshortener.dto.OriginalUrlResponse;
import ru.rashev.urlshortener.dto.ShorteningRequest;
import ru.rashev.urlshortener.dto.ShorteningResponse;
import ru.rashev.urlshortener.service.ShorteningService;

import java.util.Optional;

/**
 * @author konstantin-rashev on 27/08/2019.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApplicationConfig config;
    private final ShorteningService shorteningService;

    public ApiController(ApplicationConfig config, ShorteningService shorteningService) {
        this.config = config;
        this.shorteningService = shorteningService;
    }

    @ResponseBody
    @RequestMapping(value = "shortenUrl", method = RequestMethod.POST)
    public ShorteningResponse shortenUrl(@RequestBody ShorteningRequest request) {
        String shortUrlDomain = request.getDomain() != null ? request.getDomain() : config.getDefaultDomain();
        return new ShorteningResponse(shorteningService.buildShortUrlFor(request.getOriginalUrl(), shortUrlDomain));
    }

    @ResponseBody
    @RequestMapping(value = "originalUrl", method = RequestMethod.GET)
    public OriginalUrlResponse getOriginalUrl(@RequestBody OriginalUrlRequest request) {
        Optional<String> optionalOriginalUrl = shorteningService.getOriginalUrlBy(request.getShortUrlId(),
                request.getShortUrlDomain());
        return optionalOriginalUrl
                .map(s -> new OriginalUrlResponse(s, true))
                .orElseGet(() -> new OriginalUrlResponse(null, false));
    }
}
