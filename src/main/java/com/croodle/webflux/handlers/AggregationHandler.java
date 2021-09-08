package com.croodle.webflux.handlers;

import com.croodle.webflux.model.AggregatedOutput;
import com.tnt.assessment.resultprocessors.PricingResult;
import com.tnt.assessment.resultprocessors.ResultInterface;
import com.tnt.assessment.resultprocessors.ShippingResult;
import com.tnt.assessment.resultprocessors.TrackingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

@Component
public class AggregationHandler {

  @Autowired
  private PricingHandler pricingHandler;
  @Autowired
  private ShippingHandler shippingHandler;
  @Autowired
  private TrackingHandler trackingHandler;

  public Mono<AggregatedOutput> aggregate(String pricing, String track, String shipments) {
    AggregatedOutput results = new AggregatedOutput();
    List<Mono<Object>> aggregateHandlers = new ArrayList<>();
    createRequests(splitRequest(pricing), results, pricingHandler, aggregateHandlers, new PricingResult(results.getPricing()));
    createRequests(splitRequest(track), results, trackingHandler, aggregateHandlers, new TrackingResult(results.getTrack()));
    createRequests(splitRequest(shipments), results, shippingHandler, aggregateHandlers, new ShippingResult(results.getShipments()));

    return Mono.when(aggregateHandlers).then(Mono.just(results));
  }

  private void createRequests(Optional<String[]>  inputs,
                              AggregatedOutput results,
                              GenericHandler handler,
                              List<Mono<Object>> aggregateHandlers,
                              ResultInterface resultProcessor) {
    inputs.ifPresent(strings -> Arrays.stream(strings).forEach(entry -> {
      if (!resultProcessor.containsKey(entry)) {
        resultProcessor.addEmptyEntry(entry);
        aggregateHandlers.add(handler.get(entry)
            .doOnSuccess(result -> resultProcessor.processResult(entry, result))
            .timeout(Duration.ofMillis(60000))
            .doOnCancel(() -> resultProcessor.addEmptyEntry(entry))
        );
      }
    }));
  }

  public Optional<String[]> splitRequest(String input) {
    if (input == null || input.trim().isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(input.trim().split(","));
  }

}
