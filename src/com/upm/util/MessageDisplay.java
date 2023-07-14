package com.upm.util;

import com.github.javaparser.ast.expr.SimpleName;

import java.util.Arrays;

public interface MessageDisplay {

    static void ShowMessage(String message , int lineNumber, String documentationLink, String className){
        System.out.println("---------------------------------------------------");
        System.out.println("************ a Bug has been Found  ************");

        System.out.println(
                         "Bug Description   : "+message +"\n"+
                         "Found in Class    : "+className +"\n"+
                         "Found in Line Number        : "+lineNumber+"\n"+
                        "See Documentation Link      : "+" https://rules.sonarsource.com/"+documentationLink);

        System.out.println("---------------------------------------------------");
        System.out.println("");


    }

    static void startFixingMessage(String ruleId){

        System.out.println("---------------------------------------------------");
        System.out.println(" Start Fixing Bug =  " +
                ( ruleId.split("/")[ruleId.split("/").length-1]) +
                "" +
                "");
        System.out.println("---------------------------------------------------");

    }

    static void finishFixingMessage(String ruleId){

        System.out.println(" Finishing Fixing Bug =  " +
                ( ruleId.split("/")[ruleId.split("/").length-1]) +
                "" +
                "");
        System.out.println("---------------------------------------------------");

    }




}
