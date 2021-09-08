package com.croodle.webflux.repoitory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class APIRepository {

  @Value( "${your_server}" )
  private String yourServer;

  private static final Logger LOG = LoggerFactory.getLogger(APIRepository.class);

  public void getBatchAsync(List<String> ids, MonoSink<Map> monoSink, String baseURL) {
    WebClient.create()
        .get()
        .uri(getUrlFromIds(ids, baseURL))
        .retrieve()
        .bodyToMono(Map.class)
        .onErrorResume(e -> {
          LOG.error("HTTP Failure");
          return Mono.just(new HashMap<>());
        })
        .subscribe(objects -> {
          if (objects != null) {
            LOG.debug(objects.toString());
          } else {
            objects = new HashMap();
          }
          monoSink.success(objects);
        });
  }

  private String getUrlFromIds(List<String> ids, String baseURL){
    String url = yourServer + baseURL+ String.join(",", ids);
    System.out.println(url);
    return url;
  }
}
