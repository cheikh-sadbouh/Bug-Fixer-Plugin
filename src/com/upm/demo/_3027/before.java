package com.upm.demo._3027;

import java.io.IOException;

public class before {



 public   void myfn(String input) {
  int indexOf = input.indexOf("t");  // Noncompliant
  int lastIndexOf = input.lastIndexOf("c");  // Noncompliant
 }
}
