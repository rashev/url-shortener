package ru.rashev.urlshortener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rashev.urlshortener.config.ApplicationConfig;
import ru.rashev.urlshortener.dto.OriginalUrlRequest;
import ru.rashev.urlshortener.dto.OriginalUrlResponse;
import ru.rashev.urlshortener.dto.ShorteningRequest;
import ru.rashev.urlshortener.dto.ShorteningResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
@FixMethodOrder
public class UrlShortenerApplicationTests {

	private int port = 8088;

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ApplicationConfig applicationConfig;

	private String originalUrl;

	@Before
	public void setup() {
		originalUrl = "http://domain.com/long_path?param1=val1&param2=val2";
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void shortAndResolveUrl() throws URISyntaxException, MalformedURLException {
		ShorteningRequest shorteningRequest = new ShorteningRequest(originalUrl, null);
		RequestEntity<ShorteningRequest> shortenRequestEntity = new RequestEntity<>(
				shorteningRequest,
				HttpMethod.POST,
				new URI("http://localhost:" + port + "/api/shortenUrl")
		);
		ResponseEntity<ShorteningResponse> shortenResponse = restTemplate.exchange(shortenRequestEntity, ShorteningResponse.class);
		Assert.assertEquals(200, shortenResponse.getStatusCode().value());
		Assert.assertNotNull(shortenResponse.getBody());
		Assert.assertFalse(shortenResponse.getBody().getShortUrl().isEmpty());

		String shortUrl = shortenResponse.getBody().getShortUrl();
		String shortUrlId = shortUrl.substring(shortUrl.lastIndexOf('/') + 1);

		OriginalUrlRequest originalUrlRequest = new OriginalUrlRequest(shortUrlId, applicationConfig.getDefaultDomain());
		RequestEntity<OriginalUrlRequest> originalUrlRequestEntity = new RequestEntity<>(
				originalUrlRequest,
				HttpMethod.POST,
				new URI("http://localhost:" + port + "/api/originalUrl")
		);
		ResponseEntity<OriginalUrlResponse> originalUrlResponse = restTemplate.exchange(originalUrlRequestEntity,
				OriginalUrlResponse.class);

		Assert.assertEquals(200, originalUrlResponse.getStatusCode().value());
		Assert.assertNotNull(originalUrlResponse.getBody());
		Assert.assertTrue(originalUrlResponse.getBody().isExists());
		Assert.assertEquals(originalUrl, originalUrlResponse.getBody().getOriginalUrl());
	}
}
