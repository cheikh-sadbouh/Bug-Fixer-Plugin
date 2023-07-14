package com.upm.demo._3985;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.BugCriticalDetector;
import com.upm.detector.CodeSmellMajorDetector;
import com.upm.fixer.BugCriticalFixer;
import com.upm.fixer.CodeSmellMajorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_3985_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_3985\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));

        if (CodeSmellMajorDetector.RSPEC_3985(astTree)) {
            CodeSmellMajorFixer.RSPEC_3985_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
