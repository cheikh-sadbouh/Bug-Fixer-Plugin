package com.upm.util;

import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public interface ASTHelper {


    static String getVariableType(CompilationUnit cu, String variableName) {
        AtomicReference<String> returnedValue = new AtomicReference<>("");
        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(method -> {
                if (method.getBody().get().isExpressionStmt()) {
                    if (method.getBody().get().asExpressionStmt().getExpression().isVariableDeclarationExpr()) {
                        method.getBody().get().asExpressionStmt().getExpression()
                                .asVariableDeclarationExpr().getVariables().forEach(v -> {

                            if (variableName.equals(v.getName().asString())) {
                                returnedValue.set(v.getType().asClassOrInterfaceType().getName().getIdentifier());

                            }
                        });
                    }


                }
            });
        });


        return returnedValue.get();
    }


    static void changeVariableType(CompilationUnit ce, String variableName) {
        ce.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
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

                                if (variableName.equals(variableDeclarator.getName().asString())) {
                                    //   statement.remove();
                                   /* method.getBody().get().addStatement(0,
                                            createAndGetVariable(
                                                    variableName,
                                                    "StringBuilder",
                                                    "new StringBuilder()"));*/
                                    // statement.asExpressionStmt().setExpression( createAndGetVariable("variableName", "me", "new me()"));
                                    //  method.getBody().get().asBlockStmt().getStatements().remove(tobedeleted);
                                    method.accept(new ModifierVisitor<Visitable>() {
                                        @Override
                                        public VariableDeclarator visit(VariableDeclarator n, Visitable arg) {
                                            // return super.visit(n, arg);


                                            if (variableName.equals(n.getName().asString())) {
                                                //n.remove();
                                                VariableDeclarator variableDeclarator = new VariableDeclarator();
                                                n.setName(variableName);
                                                n.setType("StringBuilder");

                                                n.setInitializer("new StringBuilder()");


                                                //n.removeInitializer();


                                                // ce.replace(n, variableDeclarator);
                                                // return null;
                                            }
                                            return null;
                                        }
                                    }, null);

                                    break;

                                    // variableDeclarator.setName("chs")
                                    //method.remove(variableDeclarator);
                                    // statement.remove(variableDeclarator);
                                    //variableDeclarator.setType(new ClassOrInterfaceType("StringBuilder"));
                                    //  variableDeclarator.setInitializer("new StringBuilder()");
                                }

                            }
                       /*   statement.asExpressionStmt().getExpression()
                                    .asVariableDeclarationExpr().getVariables().forEach(v->{



                                if(variableName.equals(v.getName().asString())){
                                    System.out.println( "line = "+v.getBegin().get().line);
                                    method.getBody().get().addStatement(v.getBegin().get().line,createAndGetVariable("variableName","me","new me()"));

                                  //  statement.as("StringBuilder bld = new StringBuilder();");

                                  *//*  statement.asExpressionStmt().getExpression()
                                            .asVariableDeclarationExpr().setVariables(new NodeList(createAndGetVariable(
                                            "bld",
                                            "StringBuilder",
                                            "new StringBuilder()")));*//*
                            //       v.setInitializer("new StringBuilder()");
                              //      v.setType(new ClassOrInterfaceType("StringBuilder"));

*//*
                                    method.accept(new ModifierVisitor<Visitable>(){
                                        @Override
                                        public Visitable visit(VariableDeclarator n, Visitable arg) {
                                            // return super.visit(n, arg);

                                            if(variableName.equals(n.getName().asString())){
                                              // n.remove();
                                                VariableDeclarator variableDeclarator = new VariableDeclarator();
                                                variableDeclarator.setName("variableName");
                                                variableDeclarator.setType(new ClassOrInterfaceType("variableType"));
                                                variableDeclarator.setInitializer("varaibleInitializer");
                                                 //n.removeInitializer();
                                                 //= variableDeclarator;

                                              //  System.out.println( "line = "+n.getBegin().get().in);


                                                // ce.replace(n, variableDeclarator);
                                               // return null;
                                            }
                                            return null;
                                        }
                                    }, null);*//*


                                }
                            });*/
                        }

                    }
                }


            });
        });


    }

    static ExpressionStmt createVariableAsExpression(String variableName, String variableType, String varaibleInitializer) {
        ExpressionStmt expressionStmt = new ExpressionStmt();
        VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr();
        VariableDeclarator variableDeclarator = new VariableDeclarator();

        variableDeclarator.setName(variableName);
        variableDeclarator.setType(new ClassOrInterfaceType(variableType));
        variableDeclarator.setInitializer(varaibleInitializer);
        NodeList<VariableDeclarator> variableDeclarators = new NodeList<>();
        variableDeclarators.add(variableDeclarator);
        variableDeclarationExpr.setVariables(variableDeclarators);
        expressionStmt.setExpression(variableDeclarationExpr);
        return expressionStmt;
    }


    static FieldDeclaration createFieldWithInitializer(
            String fieldName,
            String fieldType,
            String fieldInitializer,
            final EnumSet<Modifier> modifiers
    ) {

        VariableDeclarator variableDeclarator = new VariableDeclarator();
        variableDeclarator.setName(fieldName);
        variableDeclarator.setType(fieldType);
        variableDeclarator.setInitializer(fieldInitializer);

        FieldDeclaration fieldDeclaration = new FieldDeclaration();
        fieldDeclaration.setModifiers(modifiers);

        fieldDeclaration.getVariables().addLast(variableDeclarator);

        return  fieldDeclaration;

    }


    static Node getNodeAt(int line, int column, CompilationUnit cu) {

        return getNodeAt(line, column, cu.findRootNode(), node -> {
            // Verify the node range can be accessed
            if (!node.getBegin().isPresent() || !node.getEnd().isPresent())
                return false;
            // Should be fine
            return true;
        });
    }

    static Node getNodeAt(int line, int column, Node root, Predicate<Node> filter) {
        if (!filter.test(root))
            return null;
        // Check cursor is in bounds
        // We won't instantly return null because the root range may be SMALLER than
        // the range of children.
        boolean bounds = true;
        Position cursor = Position.pos(line, column);
        if (cursor.isBefore(root.getBegin().get()) || cursor.isAfter(root.getEnd().get()))
            bounds = false;
        // Iterate over children, return non-null child
        for (Node child : root.getChildNodes()) {
            Node ret = getNodeAt(line, column, child, filter);
            if (ret != null)
                return ret;
        }
        // If we're not in bounds and none of our children are THEN we assume this node is bad.
        if (!bounds)
            return null;
        // In bounds so we're good!
        return root;
    }


/*

                   System.out.println(
                                        getNodeAt(st.getBegin().get().line
                                                ,st.getBegin().get().column
                                                , cu));


                                getNodeAt(st.getBegin().get().line
                                        ,st.getBegin().get().column
                                        , cu).getParentNode().get().getChildNodes().forEach(node -> {

                                    ( (IfStmt)node).getCondition().asMethodCallExpr().setName("changed");

                                            System.out.println(
                                                    ( (IfStmt)node).getCondition().toString()
                                            );

                                    System.out.println(
                                           cu
                                    );
                                });
 */


    static boolean isStringLiteralAnAnnotationArgument(String stringLiteral, CompilationUnit cu){
        AtomicBoolean result = new AtomicBoolean(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().findAny()
                .get().getMembers().forEach(annotationExpr -> {

            if(annotationExpr.isMethodDeclaration()){

                annotationExpr.asMethodDeclaration().getAnnotations().forEach(annotationExpr1 -> {


                    if( annotationExpr1.toString().contains(stringLiteral)){
                        result.set(true);

                    }
                });

            }else if(annotationExpr.isFieldDeclaration()){


                annotationExpr.asFieldDeclaration().getAnnotations().forEach(annotationExpr1 -> {
                    if(annotationExpr1.toString().contains(stringLiteral)){
                        result.set(true);

                    }
                });
            }
        });


        return result.get();
    }
}
