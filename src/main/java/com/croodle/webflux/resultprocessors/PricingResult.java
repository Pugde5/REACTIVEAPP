package com.croodle.webflux.resultprocessors;

import java.util.Map;
import java.util.Optional;

public class PricingResult extends ResultInterface<Double> {

  //what should I return if there was an error. It seems null, if anyone changes there
  //mind then change this....
  private static final Double ERROR = null;

  public PricingResult(Map<String, Double> toProcessMap) {
    this.toProcessMap = toProcessMap;
  }

  @Override
  public void processResult(String id, Object entry) {
    if (entry instanceof Double) {
      toProcessMap.put(id, (Double) entry);
    } else {
      if (entry instanceof Optional) {
        setErrorEntry(id);
      }
    }
  }

  @Override
  public void setErrorEntry(String id) {
    toProcessMap.put(id, ERROR);
  }
}
