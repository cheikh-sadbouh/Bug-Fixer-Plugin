package com.upm;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;
import com.upm.detector.CodeSmellMajorDetector;
import com.upm.fixer.BugCriticalFixer;
import com.upm.util.FileOperation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class PluginMain {

    public static  void main(String args[]){

        try {
            Files.walk(Path.of("path_of_project"))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                   // .map(Path::toFile)
                    .collect(Collectors.toList())
                    .forEach(file->{
                        try {
                            CompilationUnit cu = JavaParser.parse(file.toFile());
                            System.out.println("------------------ start analysing "+file.getFileName()+"------------------------");
                            //BugCriticalFixer.RSPEC_1143_FIX(cu);
                           // BugCriticalFixer.RSPEC_1175_FIX(cu);
                           // CodeSmellMajorDetector.RSPEC_3985(cu);
                            CodeSmellMajorDetector.RSPEC_3042(cu);
                           // YamlPrinter printer = new YamlPrinter(true);
                         //   System.out.println(printer.output(cu));
                            FileOperation.saveAstChanges(file.toFile().getPath(),cu);

                            System.out.println("------------------end analysing "+file.getFileName()+"------------------------");

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        System.out.println(file.getFileName());
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
