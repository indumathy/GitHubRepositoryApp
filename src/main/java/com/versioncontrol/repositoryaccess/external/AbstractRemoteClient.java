package com.versioncontrol.repositoryaccess.external;

import static java.util.Collections.singletonList;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/*
Abstract class for client implementation
 */
@Slf4j
public abstract class AbstractRemoteClient<C> {

  protected final RestTemplate remoteClient;
  protected final C configuration;

  protected AbstractRemoteClient(C configuration) {
    this.remoteClient = new RestTemplate();
    this.configuration = configuration;
  }

  /*
  add query param to url
   */
  protected UriComponentsBuilder addStringQueryParameter(UriComponentsBuilder builder,
      String paramName, String value) {
    if (StringUtils.isNotEmpty(value)) {
      builder = builder.queryParam(paramName, value);
    }
    return builder;
  }

  /*
  Common method to initialize header
   */
  public HttpEntity<Object> initializeHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity<>(headers);
  }


}
