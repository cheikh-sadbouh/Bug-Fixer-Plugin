package com.upm.demo._4973;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.CodeSmellMinorDetector;
import com.upm.fixer.CodeSmellMinorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_4973_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_4973\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));

        if (CodeSmellMinorDetector.RSPEC_4973(astTree)) {
            CodeSmellMinorFixer.RSPEC_4973_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
