package com.croodle.webflux.handlers;

import com.croodle.webflux.queue.FluxQueue;
import com.croodle.webflux.repoitory.APIRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Supplier;

@Service
public class PricingHandler extends AbstractHandler {

  public PricingHandler(APIRepository repository) {
    super(repository);
  }

  @Override
  public String getBaseURL() {
    return PRICINGURL;
  }

}
