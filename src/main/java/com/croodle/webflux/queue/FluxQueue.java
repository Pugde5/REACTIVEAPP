package com.croodle.webflux.queue;

import java.util.ArrayList;
import java.util.List;

public class FluxQueue {
  private ArrayList<String> queue;
  public int currentSize = -1;

  public List<String> get() {
    currentSize = 0;
    queue = new ArrayList<String>();
    return queue;
  }

  public void pop() {
    currentSize = 0;
  }

  public int getSize() {
    int returnSize = (currentSize++ / 5);
    return returnSize;
  }
}
