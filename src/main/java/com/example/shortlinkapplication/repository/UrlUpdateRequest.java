package com.example.shortlinkapplication.repository;

import lombok.Data;

@Data
public class UrlUpdateRequest {

  Integer id;
  String longUrl;
  String shortUrl;

}
