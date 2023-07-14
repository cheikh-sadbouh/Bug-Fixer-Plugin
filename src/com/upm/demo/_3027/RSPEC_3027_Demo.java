package com.upm.demo._3027;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.CodeSmellMajorDetector;
import com.upm.detector.CodeSmellMinorDetector;
import com.upm.fixer.CodeSmellMajorFixer;
import com.upm.fixer.CodeSmellMinorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_3027_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_3027\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));
        if (CodeSmellMinorDetector.RSPEC_3027(astTree)) {
            CodeSmellMinorFixer.RSPEC_3027_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
