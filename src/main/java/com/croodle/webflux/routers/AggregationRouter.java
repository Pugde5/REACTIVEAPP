package com.croodle.webflux.routers;

import com.croodle.webflux.handlers.AggregationHandler;
import com.croodle.webflux.model.AggregatedOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController()
@RequestMapping("/")
public class AggregationRouter {

  private static final Logger LOG = LoggerFactory.getLogger(AggregationRouter.class);

  @Autowired
  private AggregationHandler aggregationHandler;

  @GetMapping("/aggregation")
  public Mono<AggregatedOutput> get(@RequestParam(required = false) String pricing,
                                    @RequestParam(required = false) String track,
                                    @RequestParam(required = false) String shipments) {
    return aggregationHandler.aggregate(pricing, track, shipments)
        .timeout(Duration.ofMillis(120000))
        .doOnNext(result -> LOG.debug("transforming product: {}", result))
        .onErrorResume(e -> {
          LOG.error("Unexpected Error in Aggregation", e);
          return Mono.just(new AggregatedOutput());
        });
  }
}
