package com.upm;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.YamlPrinter;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;


import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.github.javaparser.ast.body.MethodDeclaration;

import static java.time.Period.parse;

public  class plugin extends AnAction {
    private Project project;
    static String getClassName(CompilationUnit compilationUnit) {
        return compilationUnit.getTypes().get(0).getName().toString();
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
           CompilationUnit cu = null;
        try {
         //   cu = parse(new File("file.java"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        YamlPrinter printer = new YamlPrinter(true);
        System.out.println(printer.output(cu));

        Optional<ClassOrInterfaceDeclaration> classX = cu.getClassByName(getClassName(cu));
        //   YamlPrinter printer = new YamlPrinter(true);

        //  System.out.println(printer.output(cu));
        CompilationUnit finalCu = cu;
        classX.ifPresent(
                clx -> {

                    for (MethodDeclaration method : classX.get().getMethods()) {
                        method.accept(
                                new VoidVisitorAdapter<Void>() {

                                    @Override
                                    public void visit(MethodDeclaration m, Void arg) {

                                       // Statement statement = JavaParser.parseStatement("new ResponseEntity().ok()");
                                       // m.getBody().get().getStatements().get(2).asBlockStmt()..setExpression(statement);
                                     //   m.asMethodDeclaration().getType().asClassOrInterfaceType().setName("ResponseEntity");

                                        // m.getBody().get().getStatements().addLast(statement);

                                       // m.getBody().get().asBlockStmt().getStatements()
                                       // m.asMethodDeclaration().getType().asClassOrInterfaceType().setName("ResponseEntity");
                                        m.setName("xx");
                                        String fileLocation="file.java";
                                        try {
                                            Files.write(
                                                    new File(fileLocation).toPath(),
                                                    finalCu.toString().getBytes(Charset.defaultCharset()),
                                                    StandardOpenOption.TRUNCATE_EXISTING);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        super.visit(m, arg);
                                    }
                                },
                                null);
                    }
                });
        //anActionEvent.getProject();
        //final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);

      /*  Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }

        System.out.println(activeProject.getName());
        System.out.println(activeProject.getProjectFilePath());
        System.out.println(activeProject.getBasePath());*/

      //  Path start = Paths.get(Objects.requireNonNull(activeProject.getBasePath()));



     // YamlPrinter printer = new YamlPrinter(true);
      //  System.out.println(printer.output(cu));
 /*       try {
         Files.walk(Path.of(anActionEvent.getProject().getBasePath()))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .collect(Collectors.toList())
         .forEach(file->{


         });

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
*/

;
      /*  System.out.println(
                Arrays.stream(anActionEvent.getData(LangDataKeys.PSI_FILE)
                        .getChildren())
                        .collect(Collectors.toList()));*/
       // System.out.println(e.getNode().getPsi().getTextRange());

     /*   int lineNum = anActionEvent.getData(CommonDataKeys.EDITOR).getDocument().getLineNumber(needHighlightPsiElement.getTextRange().getStartOffset());
        final TextAttributes textattributes = new TextAttributes(null, backgroundColor, null, EffectType.LINE_UNDERSCORE, Font.PLAIN);
        final Project project = needHighlightPsiElement.getProject();
        final FileEditorManager editorManager = FileEditorManager.getInstance(project);
        final Editor editor = editorManager.getSelectedTextEditor();
        editor.getMarkupModel().addLineHighlighter(lineNum, HighlighterLayer.CARET_ROW, textattributes);*/




    }


     void saveAstChanges(String fileLocation, CompilationUnit cu) {
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


