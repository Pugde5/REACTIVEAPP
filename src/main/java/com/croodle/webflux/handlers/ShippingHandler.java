package com.croodle.webflux.handlers;

import org.springframework.stereotype.Service;

import com.croodle.webflux.repoitory.APIRepository;

@Service
public class ShippingHandler  extends AbstractHandler {

  public ShippingHandler(APIRepository repository) {
    super(repository);
  }

  @Override
  public String getBaseURL() {
    return SHIPPINGURL;
  }

}
