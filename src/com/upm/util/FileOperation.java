package com.upm.util;

import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public interface FileOperation {


    static void saveAstChanges(String fileLocation, CompilationUnit cu) {
        try {

            Files.write(
                    new File(fileLocation).toPath(),
                    cu.toString().getBytes(Charset.defaultCharset()),
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
