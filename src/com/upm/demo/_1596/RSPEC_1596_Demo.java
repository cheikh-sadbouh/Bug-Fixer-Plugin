package com.upm.demo._1596;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.BugMajorDetector;
import com.upm.detector.CodeSmellMinorDetector;
import com.upm.fixer.BugMajorFixer;
import com.upm.fixer.CodeSmellMinorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_1596_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_1596\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));
        if (CodeSmellMinorDetector.RSPEC_1596(astTree)) {
            CodeSmellMinorFixer.RSPEC_1596_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
