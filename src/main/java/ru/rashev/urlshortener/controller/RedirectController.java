package ru.rashev.urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.rashev.urlshortener.service.ShorteningService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author konstantin-rashev on 31/08/2019.
 */
@RestController
public class RedirectController {
    private static final Logger log = LoggerFactory.getLogger(RedirectController.class);

    private final ShorteningService shorteningService;

    public RedirectController(ShorteningService shorteningService) {
        this.shorteningService = shorteningService;
    }

    @RequestMapping(path = "/{shortUrlId}", method = RequestMethod.GET)
    ResponseEntity<String> processShortUrl(@PathVariable String shortUrlId, HttpServletRequest request) {
        try {
            Optional<String> originalUrl = shorteningService.getOriginalUrlBy(shortUrlId, request.getServerName());
            ResponseEntity<String> responseEntity;
            if (originalUrl.isPresent()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", originalUrl.get());
                headers.add("Connection", "close");
                responseEntity = new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
            } else {
                responseEntity = new ResponseEntity<>("Page not found", HttpStatus.NOT_FOUND);
            }
            return responseEntity;
        } catch (Exception ex) {
            log.error("Unexpected error during execution processShortUrl request", ex);
            throw new RuntimeException("Unable to process request");
        }
    }
}
