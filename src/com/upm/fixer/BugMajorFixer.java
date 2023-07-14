package com.upm.fixer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.upm.util.MessageDisplay;

import java.util.concurrent.atomic.AtomicReference;

public interface BugMajorFixer {


    static boolean RSPEC_2111_FIX(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-2111";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getFields().forEach(f -> {
                f.asFieldDeclaration().getVariables().forEach(v -> {
                    v.getInitializer().get().asObjectCreationExpr().getArguments().forEach(arg -> {

                    });
                    if("BigDecimal".equals(v.getType().asString()))
                    {


                        MessageDisplay.startFixingMessage(documentationURI);
                        DoubleLiteralExpr argvalue = v.getInitializer().get().asObjectCreationExpr().getArgument(0).asDoubleLiteralExpr();
                        v.getInitializer().get().asObjectCreationExpr().setArgument(0, new StringLiteralExpr(""+argvalue.getValue()+""));
                        MessageDisplay.finishFixingMessage(documentationURI);

                        isFound.set(true);
                    }




                });
            });
        });
        return isFound.get();
    }
}
