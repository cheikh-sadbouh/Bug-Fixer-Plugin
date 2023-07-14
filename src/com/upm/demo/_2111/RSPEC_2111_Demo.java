package com.upm.demo._2111;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;
import com.upm.detector.BugMajorDetector;
import com.upm.fixer.BugMajorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_2111_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_2111\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));
        if (BugMajorDetector.RSPEC_2111(astTree)) {
            BugMajorFixer.RSPEC_2111_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
