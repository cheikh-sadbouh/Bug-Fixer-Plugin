package com.upm.demo._1192;

public class after {

    public static final String DUPLICATED_STRING_8 = "action1";

    public void run() {
        // Noncompliant - "action1" is duplicated 3 times
        prepare(DUPLICATED_STRING_8);
        execute(DUPLICATED_STRING_8);
        release(DUPLICATED_STRING_8);
    }

    private void release(String action1) {
    }

    private void execute(String action1) {
    }

    private void prepare(String action1) {
    }

    // Compliant - annotations are excluded
    @SuppressWarning(key = "all")
    private void method1() {
    /* ... */
    }

    @SuppressWarning(key = "all")
    private void method2() {
    /* ... */
    }

    public String method3(String a) {
        // Compliant - literal "'" has less than 5 characters and is excluded
        System.out.println("'" + a + "'");
        // Compliant - literal "" has less than 5 characters and is excluded
        return "";
    }
}
