package com.upm.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.upm.util.MessageDisplay;
import org.apache.velocity.runtime.directive.Break;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.upm.fixer.CodeSmellMinorFixer.RSPEC_1192_FIX;
import static com.upm.util.ASTHelper.*;

public interface CodeSmellMinorDetector {

    /*
     * "Collections.EMPTY_LIST", "EMPTY_MAP", and "EMPTY_SET" should not be used
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1596
     *
     * */

    static boolean RSPEC_1596(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1596";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getFields().forEach(f -> {
                f.asFieldDeclaration().getVariables().forEach(v -> {


                    if (v.getInitializer().get().isFieldAccessExpr()) {
                        if ("EMPTY_LIST".equals(v.getInitializer().get().asFieldAccessExpr().getName().getIdentifier())) {
                            v.setInitializer("Collections.emptyList()");
                            MessageDisplay.ShowMessage(
                                    "\"Collections.EMPTY_LIST\" should not be used"
                                    , v.getBegin().get().line
                                    , documentationURI,
                                    c.asClassOrInterfaceDeclaration().getName().asString());

                            isFound.set(true);
                            return;
                        }
                        if ("EMPTY_MAP".equals(v.getInitializer().get().asFieldAccessExpr().getName().getIdentifier())) {
                            MessageDisplay.ShowMessage(
                                    " \"EMPTY_MAP\" should not be used"
                                    , v.getBegin().get().line
                                    , documentationURI,
                                    c.asClassOrInterfaceDeclaration().getName().asString());

                            isFound.set(true);
                            return;
                        }
                        if ("EMPTY_SET".equals(v.getInitializer().get().asFieldAccessExpr().getName().getIdentifier())) {
                            MessageDisplay.ShowMessage(
                                    "\"EMPTY_SET\" should not be used"
                                    , v.getBegin().get().line
                                    , documentationURI,
                                    c.asClassOrInterfaceDeclaration().getName().asString());

                            isFound.set(true);
                            return;
                        }

                    }

                });


            });
        });
        return isFound.get();
    }


    /*
     * Collection.isEmpty() should be used to test for emptiness
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1155
     *
     * */

    static boolean RSPEC_1155(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1155";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(e -> {
                e.getBody().get().getStatements().forEach(st -> {
                    if (st.isIfStmt()) {
                        if (st.asIfStmt().getCondition().isBinaryExpr()) {
                            if (st.asIfStmt().getCondition().asBinaryExpr().getLeft().isMethodCallExpr()) {
                                if ("size".equals(st.asIfStmt().getCondition().asBinaryExpr().getLeft().asMethodCallExpr().getName().getIdentifier())) {

                                    MessageDisplay.ShowMessage(
                                            "Collection.isEmpty() should be used to test for emptiness"
                                            , st.getBegin().get().line
                                            , documentationURI,
                                            c.asClassOrInterfaceDeclaration().getName().asString());


                                    isFound.set(true);
                                }
                            }
                        }
                    }
                });
            });
        });
        return isFound.get();
    }


    /*
     * Strings literals should be placed on the left side when checking for equality
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1132
     *
     * */

    static boolean RSPEC_1132(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1132";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {

                method.getBody().get().getStatements().forEach(st -> {
                    if (st.isIfStmt()) {
                        if (st.asIfStmt().getCondition().isMethodCallExpr()) {
                            if (st.asIfStmt().getCondition().asMethodCallExpr().getScope().get().isNameExpr() &&
                                    st.asIfStmt().getCondition().asMethodCallExpr().getArgument(0).isStringLiteralExpr()
                                    && st.asIfStmt().getCondition().asMethodCallExpr().getName().asString().equals("equals")
                            ) {

                                MessageDisplay.ShowMessage(
                                        "Strings literals should be placed on the left side when checking for equality"
                                        , st.getBegin().get().line
                                        , documentationURI,
                                        c.asClassOrInterfaceDeclaration().getName().asString());


                                isFound.set(true);
                            }


                        }
                    }
                });
            });
        });


        return isFound.get();
    }


    /*
     * Strings and Boxed types should be compared using "equals()"
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-4973
     *
     * */

    static boolean RSPEC_4973(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-4973";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {

                method.getBody().get().getStatements().forEach(st -> {
                    if (st.isIfStmt()) {
                        if (st.asIfStmt().getCondition().isBinaryExpr()) {
                            if (st.asIfStmt().getCondition().asBinaryExpr().getOperator().asString().equals("==")
                                    && st.asIfStmt().getCondition().asBinaryExpr().getLeft().isNameExpr()
                                    && st.asIfStmt().getCondition().asBinaryExpr().getRight().isNameExpr()
                            ) {


                                MessageDisplay.ShowMessage(
                                        "Strings and Boxed types should be compared using \"equals()\""
                                        , st.getBegin().get().line
                                        , documentationURI,
                                        c.asClassOrInterfaceDeclaration().getName().asString());
                                isFound.set(true);

                            }


                        }
                    }
                });
            });
        });


        return isFound.get();
    }



    /*
     * Strings should not be concatenated using '+' in a loop
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1643
     *
     * */

    static boolean RSPEC_1643(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1643";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);
        AtomicReference<String> varname = new AtomicReference<>("");
        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {

                method.getBody().get().asBlockStmt().getStatements().stream().forEach(statement -> {
                    if (statement.isForStmt()) {

                        statement.asForStmt().getBody()
                                .asBlockStmt().getStatements().stream().forEach(blockStatement -> {

                            if (blockStatement.isExpressionStmt()) {


                                if (blockStatement.asExpressionStmt()
                                        .getExpression().isAssignExpr()
                                        && blockStatement.asExpressionStmt()
                                        .getExpression()
                                        .asAssignExpr()
                                        .getValue()
                                        .asBinaryExpr()
                                        .getOperator().asString().equals("+")
                                        &&
                                        getVariableType(cu, blockStatement.asExpressionStmt()
                                                .getExpression()
                                                .asAssignExpr()
                                                .getValue()
                                                .asBinaryExpr()
                                                .getLeft()
                                                .asNameExpr()
                                                .getName()
                                                .getIdentifier()).equals(
                                                getVariableType(cu, blockStatement.asExpressionStmt()
                                                        .getExpression()
                                                        .asAssignExpr()
                                                        .getTarget()
                                                        .asNameExpr()
                                                        .getName()
                                                        .getIdentifier()
                                                ))) {


                                    MessageDisplay.ShowMessage(
                                            "Strings should not be concatenated using '+' in a loop"
                                            , blockStatement.getBegin().get().line
                                            , documentationURI,
                                            c.asClassOrInterfaceDeclaration().getName().asString()
                                    );

                                    System.out.println(
                                            ((ForStmt) getNodeAt(statement.getBegin().get().line
                                                    , statement.getBegin().get().column
                                                    , cu)).asForStmt());

                                    isFound.set(true);


                                }

                            }
                        });

                    }
                });


            });
        });


        return isFound.get();
    }




    /*
     * Parsing should be used to convert "Strings" to primitives
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-2130
     *
     * */

    static boolean RSPEC_2130(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-2130";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {

                List<Statement> myList = new CopyOnWriteArrayList<>();
                myList.addAll(method.getBody().get().asBlockStmt().getStatements());

                Iterator<Statement> statementIterator = myList.iterator();


                while (statementIterator.hasNext()) {
                    Statement statement = statementIterator.next();
                    if (statement.isExpressionStmt()) {
                        if (statement.asExpressionStmt().getExpression().isVariableDeclarationExpr()) {

                            Iterator<VariableDeclarator> iter = statement.asExpressionStmt().getExpression()
                                    .asVariableDeclarationExpr().getVariables().iterator();

                            while (iter.hasNext()) {
                                VariableDeclarator variableDeclarator = iter.next();
                                if (variableDeclarator.getType().asString().equals("float")) {


                                    MessageDisplay.ShowMessage(
                                            "Parsing should be used to convert \"Strings\" to primitives"
                                            , variableDeclarator.getBegin().get().line
                                            , documentationURI,
                                            c.asClassOrInterfaceDeclaration().getName().asString());

                                    isFound.set(true);

                                }

                            }

                        }

                    }
                }

            });
        });


        return isFound.get();
    }


    /*
     * Methods should not be empty
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-1186
     *
     * */

    static boolean RSPEC_1186(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1186";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);


        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {

                if (method.getBody().get().getStatements().isEmpty()) {
                    MessageDisplay.ShowMessage(
                            "Methods should not be empty",
                            method.getBegin().get().line
                            , documentationURI,
                            c.asClassOrInterfaceDeclaration().getName().asString());
                    isFound.set(true);

                }


            });
        });


        return isFound.get();
    }



    /*
     * String literals should not be duplicated

     *
     * @see
     * https://rules.sonarsource.com/java/type/Code%20Smell/RSPEC-1192
     *
     * */


    static boolean RSPEC_1192(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-1192";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);


        for (StringLiteralExpr stringLiteralExpr : cu.findAll(StringLiteralExpr.class)) {
            final HashMap<Integer,Integer> stringCounter = new HashMap<>();




            cu.accept(new ModifierVisitor<Visitable>() {
                @Override
                public StringLiteralExpr visit(StringLiteralExpr s, Visitable arg) {

                    if (s.getValue().equalsIgnoreCase(stringLiteralExpr.getValue())
                            && stringLiteralExpr.getValue().length()>=5
                            && !isStringLiteralAnAnnotationArgument(s.getValue(), cu)) {

                        stringCounter.put(stringLiteralExpr.getBegin().get().line,
                                stringLiteralExpr.getBegin().get().column );

                    }

                    return s;
                }
            }, null);

            if (stringCounter.size()>0) {

                MessageDisplay.ShowMessage(
                        "String literals should not be duplicated",
                        stringLiteralExpr.getBegin().get().line
                        , documentationURI,
                        cu.findAll(ClassOrInterfaceDeclaration.class).stream().findFirst().get().getNameAsString() );
                isFound.set(true);
             stringCounter.forEach((l,r)-> RSPEC_1192_FIX(
                     l,
                     r
                     ,cu));



            }


        }

        return isFound.get();
    }



    /*
     * String functions use should be optimized for single characters

     *
     * @see
     * https://jira.sonarsource.com/browse/RSPEC-3027
     *
     * */


    static boolean RSPEC_3027(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-3027";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

               cu.findAll(MethodCallExpr.class)
                       .stream()
                       .filter(methodCallExpr -> "indexOf".equals(methodCallExpr.getNameAsString())
                                              || "lastIndexOf".equals(methodCallExpr.getNameAsString()))
                       .map(methodCallExpr -> {
                              if(Pattern.matches("\".\"", methodCallExpr.getArgument(0).toString())){
                                  MessageDisplay.ShowMessage(
                                          "String functions use should be optimized for single characters",
                                          methodCallExpr.getBegin().get().line
                                          , documentationURI,
                                          cu.findAll(ClassOrInterfaceDeclaration.class).stream().findFirst().get().getNameAsString()
                                  );
                                  isFound.set(true);
                              }
                           return null;
                       })
                       .toArray();


      /*
        for (MethodCallExpr methodCallExpr : cu.findAll(MethodCallExpr.class)) {

                    System.out.println(      methodCallExpr.getName()    );
            methodCallExpr.getArguments().forEach(arg -> {
                System.out.println(      arg  );
                System.out.println(  Pattern.matches("\".\"", arg.toString()) );

            });



        }*/

        return isFound.get();
    }

}

