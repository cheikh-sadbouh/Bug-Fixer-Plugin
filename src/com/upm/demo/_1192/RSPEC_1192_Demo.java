package com.upm.demo._1192;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;
import com.upm.detector.CodeSmellMinorDetector;
import com.upm.fixer.CodeSmellMinorFixer;
import com.upm.util.FileOperation;

import java.io.IOException;
import java.nio.file.Path;

public class RSPEC_1192_Demo {
    public static void main(String[] args) throws IOException {

        String fileLocation = System.getProperty("user.dir") + "\\src\\com\\upm\\demo\\_1192\\after.java";
        CompilationUnit astTree = JavaParser.parse(Path.of(fileLocation));

        YamlPrinter printer = new YamlPrinter(true);
        // System.out.println(printer.output(astTree));
        if (CodeSmellMinorDetector.RSPEC_1192(astTree)) {
          //  CodeSmellMinorFixer.RSPEC_1192_FIX(astTree);
            FileOperation.saveAstChanges(fileLocation, astTree);
        }
    }
}
