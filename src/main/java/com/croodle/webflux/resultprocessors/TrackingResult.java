package com.croodle.webflux.resultprocessors;

import java.util.Map;
import java.util.Optional;

public class TrackingResult  extends ResultInterface<String> {

  //what should I return if there was an error. It seems null, if anyone changes there
  //mind then change this....
  private static final String ERROR = null;

  private final Map<String, String> toProcessMap;

  public TrackingResult(Map<String, String> toProcessMap) {
    this.toProcessMap = toProcessMap;
  }

  @Override
  public void processResult(String id, Object entry) {
    if (entry instanceof String) {
      toProcessMap.put(id, (String) entry);
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
