package com.upm.demo._1175;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.upm.detector.BugCriticalDetector;
import com.upm.fixer.BugCriticalFixer;
import com.upm.fixer.CodeSmellMajorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

public class RSPEC_1175_Demo {

    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_1175\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));
        if (BugCriticalDetector.RSPEC_1175(astTree)) {
            BugCriticalFixer.RSPEC_1175_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }

    }

}
