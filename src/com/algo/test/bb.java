package com.algo.test;

import java.util.concurrent.atomic.AtomicLong;

public class bb {
  public static void main(String[] argv) {
    AtomicLong nextId = new AtomicLong();

    System.out.println(nextId.getAndIncrement());
  }
}

   
  