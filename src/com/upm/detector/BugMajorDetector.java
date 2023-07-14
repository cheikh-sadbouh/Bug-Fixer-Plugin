package com.upm.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.printer.YamlPrinter;
import com.upm.util.MessageDisplay;

import java.util.concurrent.atomic.AtomicReference;

public interface BugMajorDetector {


    /*
     * BigDecimal(double)" should not be used
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-2111
     *
     * */

    static boolean RSPEC_2111(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-2111";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getFields().forEach(f -> {
                f.asFieldDeclaration().getVariables().forEach(v -> {
                    v.getInitializer().get().asObjectCreationExpr().getArguments().forEach(arg -> {

                    });
                    if("BigDecimal".equals(v.getType().asString()))
                    {
                        MessageDisplay.ShowMessage(
                                "BigDecimal(double) should not be used"
                                , v.getBegin().get().line
                                , documentationURI,
                                c.asClassOrInterfaceDeclaration().getName().asString()
                        );

                        isFound.set(true);
                    }

                });
            });
        });
        return isFound.get();
    }


}
