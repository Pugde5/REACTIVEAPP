package com.croodle.webflux.resultprocessors;


import java.util.HashMap;
import java.util.Map;

public abstract class ResultInterface<T> {

  protected Map<String, T> toProcessMap = new HashMap<>();

  public abstract void processResult(String id, Object object);

  public void addEmptyEntry(String id){
    toProcessMap.put(id, null);
    }

  public abstract  void setErrorEntry(String id);

  public boolean containsKey(String id) {
    return toProcessMap.containsKey(id);
  }
}
