package com.example.shortlinkapplication.service.url;

import com.example.shortlinkapplication.dto.url.URLRequest;
import com.example.shortlinkapplication.entity.Url;
import com.example.shortlinkapplication.repository.UrlUpdateRequest;
import java.util.List;

public interface UrlService {

  List<Url> getListUrl(Integer projectID);

  String convertToShortUrl(URLRequest longUrl);

  String getLongUrl(String shortUrl);

  Url updateLongUrl(UrlUpdateRequest request);
}
