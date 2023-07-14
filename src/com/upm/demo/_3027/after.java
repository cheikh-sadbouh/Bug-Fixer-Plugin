package com.upm.demo._3027;

import java.io.IOException;

public class after {

    public void myfn(String input) {
        // Noncompliant
        int indexOf = input.indexOf('t');
        // Noncompliant
        int lastIndexOf = input.lastIndexOf('c');
    }
}
