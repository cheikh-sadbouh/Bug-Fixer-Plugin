package com.upm.demo._1143;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.upm.detector.BugCriticalDetector;
import com.upm.fixer.BugCriticalFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_1143_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_1143\\after.java";
        CompilationUnit astTree =  JavaParser.parse(Path.of(fileLocation));
        if (BugCriticalDetector.RSPEC_1143(astTree)) {
            BugCriticalFixer.RSPEC_1143_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
