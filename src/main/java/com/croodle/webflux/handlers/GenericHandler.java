package com.croodle.webflux.handlers;

import reactor.core.publisher.Mono;

public interface GenericHandler {
  Mono<Object> get(String inputString);
}
