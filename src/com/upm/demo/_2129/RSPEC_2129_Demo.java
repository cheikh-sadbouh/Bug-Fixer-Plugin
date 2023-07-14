package com.upm.demo._2129;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.CodeSmellMajorDetector;
import com.upm.fixer.CodeSmellMajorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_2129_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_2129\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));
        if (CodeSmellMajorDetector.RSPEC_2129(astTree)) {
            CodeSmellMajorFixer.RSPEC_2129_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
