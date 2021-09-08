package com.croodle.webflux.resultprocessors;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShippingResult extends ResultInterface<String[]> {

  //what should I return if there was an error. It seems null, if anyone changes there
  //mind then change this....
  private final static String[] ERROR = null;

  public ShippingResult(Map<String, String[]> toProcessMap) {
    this.toProcessMap = toProcessMap;
  }

  @Override
  public void processResult(String id, Object entry) {
    if (entry instanceof List) {
      toProcessMap.put(id,  (String[]) ((List)entry).toArray(new String[0]));
    } else {
      if (entry instanceof Optional) {
        setErrorEntry(id);
      }
    }
  }

  @Override
  public void setErrorEntry(String id) {
    toProcessMap.put(id,  ERROR);
  }
}
