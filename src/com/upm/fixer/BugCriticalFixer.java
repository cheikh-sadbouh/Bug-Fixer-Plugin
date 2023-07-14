package com.upm.fixer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.upm.util.MessageDisplay;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public interface BugCriticalFixer {


    static boolean RSPEC_1175_FIX(CompilationUnit cu) {
        AtomicReference<Boolean> isFixed = new AtomicReference<>(false);
        final String documentationURI = "java/type/Bug/RSPEC-1175";

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {
                        if (method.getName().asString().equals("finalize")){
                            MessageDisplay.startFixingMessage(documentationURI);
                            method.setName("someBetterName");
                            isFixed.set(true);
                            MessageDisplay.finishFixingMessage(documentationURI);
                            }
                    }

            );
        });


        return isFixed.get();
    }


    static boolean RSPEC_1143_FIX(CompilationUnit cu) {
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);
        final String documentationURI = "java/type/Bug/RSPEC-1143";

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(e -> {
                        e.getBody().get().getStatements().forEach(trys -> {

                                    if (trys.isTryStmt()) {
                                        for (Iterator<Statement> iterator = trys.asTryStmt().getFinallyBlock()
                                                .get().getStatements()
                                                .iterator(); iterator.hasNext();) {
                                            Statement currentstm = iterator.next();
                                            if (currentstm.isBreakStmt()) {
                                                MessageDisplay.startFixingMessage(documentationURI);
                                                iterator.remove();
                                                MessageDisplay.finishFixingMessage(documentationURI);

                                                isFound.set(true);
                                            }
                                            if (currentstm.isThrowStmt()) {
                                                MessageDisplay.startFixingMessage(documentationURI);
                                                iterator.remove();
                                                MessageDisplay.finishFixingMessage(documentationURI);

                                                isFound.set(true);
                                            }
                                            if (currentstm.isReturnStmt()) {
                                                MessageDisplay.startFixingMessage(documentationURI);
                                                iterator.remove();
                                                MessageDisplay.finishFixingMessage(documentationURI);

                                                isFound.set(true);
                                            }

                                            if (currentstm.isContinueStmt()) {
                                                MessageDisplay.startFixingMessage(documentationURI);
                                                iterator.remove();
                                                MessageDisplay.finishFixingMessage(documentationURI);

                                                isFound.set(true);
                                            }
                                        }

                                    }

                                }


                        );


                    }

            );
        });

        return isFound.get();
    }
}
