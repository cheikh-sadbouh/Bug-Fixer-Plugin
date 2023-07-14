package com.upm.demo._3751;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.CodeSmellMajorDetector;
import com.upm.fixer.CodeSmellMajorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_3751_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_3751\\after.java";
        CompilationUnit astTree =  JavaParser.parse(Path.of(fileLocation));

        if (CodeSmellMajorDetector.RSPEC_3751(astTree)) {
            CodeSmellMajorFixer.RSPEC_3751_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation,astTree);
        }
    }
}
