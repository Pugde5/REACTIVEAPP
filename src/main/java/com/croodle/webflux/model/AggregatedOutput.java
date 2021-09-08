package com.croodle.webflux.model;

import java.util.HashMap;
import java.util.Map;

public class AggregatedOutput {
  private final Map<String, Double> pricing;
  private final Map<String, String> track;
  private final Map<String, String[]> shipments;

  public AggregatedOutput() {
    pricing = new HashMap<>();
    track = new HashMap<>();
    shipments = new HashMap<>();
  }

  public Map<String, Double> getPricing() {
    return pricing;
  }

  public Map<String, String> getTrack() {
    return track;
  }

  public Map<String, String[]> getShipments() {
    return shipments;
  }
}
