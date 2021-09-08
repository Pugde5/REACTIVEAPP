package com.croodle.webflux.handlers;

import org.springframework.stereotype.Service;

import com.croodle.webflux.repoitory.APIRepository;

@Service
public class TrackingHandler  extends AbstractHandler {

  public TrackingHandler(APIRepository repository) {
    super(repository);
  }

  @Override
  public String getBaseURL() {
    return TRACKINGURL;
  }

}

