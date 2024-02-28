package com.example.shortlinkapplication.service;

import com.example.shortlinkapplication.dto.URLRequest;
import com.example.shortlinkapplication.dto.URLResponse;
import com.example.shortlinkapplication.entity.Url;
import com.example.shortlinkapplication.exception.ExpiredKeyException;
import com.example.shortlinkapplication.exception.KeyNotFoundException;
import com.example.shortlinkapplication.repository.URLRepository;
import com.example.shortlinkapplication.service.util.Base62;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class URLService {
    @Autowired
    private URLRepository urlRepository;
    private final Base62 base62;

    private String serviceUrl;
    private HttpServletRequest httpServletRequest;

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.serviceUrl = httpServletRequest.getHeader("host") + httpServletRequest.getRequestURI().split("/")[0];

    }


    /*
     * A method to call short url from url input
     * @param originalUrl
     * @return URLResponse
     */
    public URLResponse callShortUrl(String originalUrl) {
        URLResponse urlResponse = new URLResponse();
        String shortUrl = createShortUrl(originalUrl);
        urlResponse.setUrl(shortUrl);

        // save to db before returning
        Url url = new Url();
        url.setLongUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setCreationDate(LocalDate.now());
        url.setExpirationDate(LocalDate.now().plusDays(1));
        if (!urlRepository.existsByLongUrl(originalUrl)) {
            urlRepository.save(url);
        }
        return urlResponse;
    }

    /*
     * A method to create a short url from original url
     * @param originalUrl
     * @return shortLink
     */
    private String createShortUrl (String longUrl) {
        System.out.println("create short url....");
        System.out.println("exits long url in db? " + urlRepository.existsByLongUrl(longUrl));
        if (urlRepository.existsByLongUrl(longUrl))
            return urlRepository.findUrlByLongUrl(longUrl).getShortUrl();
        String shortUrlKey = base62.generateShortURL(longUrl);
        while (urlRepository.existsByShortUrl(shortUrlKey + "/" + shortUrlKey)) {
            shortUrlKey = createShortUrl(longUrl);
        }
        return serviceUrl + "/" + shortUrlKey;
    }

    public String getLongUrl (String shortUrl) {
        boolean shortUrlExits = urlRepository.existsByShortUrl(shortUrl);
        if (!shortUrlExits) {
            return null;
        }
        String longUrl = urlRepository.findUrlByShortUrl(shortUrl).getLongUrl();
        return longUrl.trim();
    }

}

