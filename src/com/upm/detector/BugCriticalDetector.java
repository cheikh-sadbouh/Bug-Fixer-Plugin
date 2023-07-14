package com.upm.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.upm.util.MessageDisplay;

import java.util.concurrent.atomic.AtomicReference;

public interface BugCriticalDetector {

    /*
     * The signature of "finalize()" should match that of "Object.finalize()"
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1175
     *
     * */
    static boolean RSPEC_1175(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1175";

        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(e -> {
                        if (e.getName().asString().equals("finalize")) {
                            MessageDisplay
                                    .ShowMessage("Method should not be  named finalize()"
                                            , e.getBegin().get().line, documentationURI
                                    , c.asClassOrInterfaceDeclaration().getName().asString());
                            isFound.set(true);
                        }
                    }

            );
        });


        return isFound.get();


    }

    /*
     * Jump statements should not occur in "finally" blocks
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1143
     *
     * */
    static boolean RSPEC_1143(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1143";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {
            System.out.println("fd= "+c.getClass().toString());

            c.getMethods().forEach(e -> {
                e.getBody().get().getStatements().forEach(trys -> {

                                    if (trys.isTryStmt()) {
                                        trys.asTryStmt().getFinallyBlock()
                                                .get().getStatements()
                                                .forEach(b -> {
                                                    if (b.isBreakStmt()) {
                                                        MessageDisplay.ShowMessage(
                                                                " break statement is found  in finally block"
                                                                , b.getBegin().get().line
                                                                , documentationURI,
                                                                c.asClassOrInterfaceDeclaration().getName().asString());
                                                        System.out.println();
                                                        isFound.set(true);
                                                    }
                                                    if (b.isThrowStmt()) {
                                                        MessageDisplay.ShowMessage(
                                                                " throw keyword  is found  in finally block"
                                                                , b.getBegin().get().line
                                                                , documentationURI,
                                                                c.asClassOrInterfaceDeclaration().getName().asString());
                                                        isFound.set(true);
                                                    }
                                                    if (b.isReturnStmt()) {
                                                        MessageDisplay.ShowMessage(
                                                                " return statement is found  in finally block"
                                                                , b.getBegin().get().line
                                                                , documentationURI,
                                                                c.asClassOrInterfaceDeclaration().getName().asString());

                                                        isFound.set(true);
                                                    }

                                                    if (b.isContinueStmt()) {
                                                        MessageDisplay.ShowMessage(
                                                                " continue statement is found  in finally block"
                                                                , b.getBegin().get().line
                                                                , "java/type/Bug/RSPEC-1143",
                                                                c.asClassOrInterfaceDeclaration().getName().asString());
                                                        isFound.set(true);
                                                    }


                                                });
                                    }

                                }


                        );


                    }

            );
        });

        return isFound.get();
    }
}
