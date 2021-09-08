package com.croodle.webflux.handlers;

import com.croodle.webflux.queue.FluxQueue;
import com.croodle.webflux.repoitory.APIRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public abstract class AbstractHandler implements GenericHandler{

  private static final Logger LOG = LoggerFactory.getLogger(AbstractHandler.class);

  private ConnectableFlux<Map> connectableFlux;
  private Disposable connectionToConnectableFlux;
  private final AtomicReference<FluxSink<String>> input = new AtomicReference<>();
  private final APIRepository repository;
  private final FluxQueue queue = new FluxQueue();

  protected static final String PRICINGURL = "/pricing?q=";
  protected static final String TRACKINGURL = "/track?q=";
  protected static final String SHIPPINGURL = "/shipments?q=";

  protected  AbstractHandler(APIRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  public void init() {
    connectableFlux = Flux.create(input::set, FluxSink.OverflowStrategy.ERROR)
        .bufferTimeout(5, Duration.ofMillis(5000), getCollectionSupplier())
        .doOnNext(ids -> {
          System.out.println("Current List: " + ids.toString());
        })
        .concatMap(ids ->
            Mono.<Map>create(sink -> repository.getBatchAsync(ids, sink, getBaseURL()))
        )
        .publish();

    connectionToConnectableFlux = connectableFlux.connect();
  }

  @PreDestroy
  public void shutdown() {
    connectionToConnectableFlux.dispose();
  }

  //Call this method to get ONE price/shippment/track by ID
  //The call returns a map of values.
  //Return in the MONO the value from the MAP that matches the id in the method signature.
  //There is something very ugly here. If no value is returned then there was an error.
  //I am flagging the error by returning an Optional.
  //It seems that you want a null if there was an error.
  //But there is a difference between null and error.
  public Mono<Object> get(String id) {
    return connectableFlux
        .doOnSubscribe(ignore -> input.get().next(id))
        .map(map -> {
          if (map.containsKey(id)) {
            return map.get(id);
          }
          return Optional.empty();
        })
        .elementAt(qetQueueSize());
  }

  protected int qetQueueSize() {
    return queue.getSize();
  }

  protected Supplier<List<String>> getCollectionSupplier() {
    return queue::get;
  }

  public abstract String getBaseURL();

}
