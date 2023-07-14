package com.upm.fixer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.printer.YamlPrinter;
import com.upm.util.MessageDisplay;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public interface CodeSmellMajorFixer
{

    static boolean RSPEC_3985_FIX(CompilationUnit cu) {
        final String documentationURI = "java/RSPEC-3985";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            if(c.isInnerClass()) {

                int counter= (int) cu.findAll(SimpleName.class).stream().filter(d -> c.asClassOrInterfaceDeclaration().getName().asString().equals(d.asString())).count();

                if(counter == 1 && c.asClassOrInterfaceDeclaration().getModifiers().toString().equals("[PRIVATE]") ){
                    MessageDisplay.startFixingMessage(documentationURI);
                    c.remove();
                    MessageDisplay.finishFixingMessage(documentationURI);

                    isFound.set(true);
                }

            }


        });


        return isFound.get();
    }
    static boolean RSPEC_3751_FIX(CompilationUnit cu) {
        final String documentationURI = "java/RSPEC-3751";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {
            c.getMethods().forEach(m->{
                m.getAnnotations().forEach(annotationExpr -> {


                    if( annotationExpr.toString().contains("@RequestMapping")
                            && !m.getModifiers().contains(Modifier.PUBLIC)){


                        MessageDisplay.startFixingMessage(documentationURI);
                        m.setModifiers(Modifier.PUBLIC.toEnumSet());
                        MessageDisplay.finishFixingMessage(documentationURI);

                        isFound.set(true);

                    }
                });
            });



        });


        return isFound.get();
    }
    static boolean RSPEC_3042_FIX(CompilationUnit cu) {
        final String documentationURI = "java/RSPEC-3042";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);


        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {

            for (MethodDeclaration method : c.getMethods()) {

                if(method.getParameters().toString().contains("ObjectOutputStream")
                        && method.getModifiers().contains(Modifier.SYNCHRONIZED)&&
                        method.getName().asString().equals("writeObject")
                ){


                    for (Iterator<Modifier> iterator = method.getModifiers().iterator(); iterator.hasNext();){
                        if(  iterator.next().toEnumSet().contains(Modifier.SYNCHRONIZED)){


                            MessageDisplay.startFixingMessage(documentationURI);
                            iterator.remove();
                            MessageDisplay.finishFixingMessage(documentationURI);

                            isFound.set(true);
                        }

                    }
                }

            }


        });


        return isFound.get();
    }
    static boolean RSPEC_2129_FIX(CompilationUnit cu) {
        final String documentationURI = "java/type/Bug/RSPEC-2129";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            c.getMethods().forEach(e -> {
                e.getBody().get().getStatements().forEach(st -> {

                    if(st.isExpressionStmt()) {

                        if( st.asExpressionStmt().getExpression().isVariableDeclarationExpr()){

                            st.asExpressionStmt().getExpression().asVariableDeclarationExpr()
                                    .getVariables().forEach(v->{
                                if(   v.getInitializer().get().isObjectCreationExpr()){


                                    String primitiveType = v.getInitializer().get().asObjectCreationExpr()
                                            .getType().getName().getIdentifier();
                                    MessageDisplay.startFixingMessage(documentationURI);

                                    switch (primitiveType){
                                        case "String":
                                            String varValues= v.getInitializer().get().asObjectCreationExpr().getArguments().get(0).asStringLiteralExpr().getValue();

                                            v.setInitializer(new NameExpr("\""+varValues+"\""));
                                            break;
                                        case "Double":
                                            String varValued = v.getInitializer().get().asObjectCreationExpr().getArguments().get(0).asDoubleLiteralExpr().getValue();

                                            v.setInitializer(new NameExpr("Double.valueOf("+varValued+")"));
                                            break;
                                        case "Integer":
                                            String varValuei= v.getInitializer().get().asObjectCreationExpr().getArguments().get(0).asIntegerLiteralExpr().getValue();

                                            v.setInitializer(new NameExpr("Integer.valueOf("+varValuei+")"));
                                            break;
                                        case "Boolean":
                                            Boolean varValueb= v.getInitializer().get().asObjectCreationExpr().getArguments().get(0).asBooleanLiteralExpr().getValue();

                                            v.setInitializer(new NameExpr("Boolean.valueOf("+varValueb+")"));
                                            break;
                                        case "BigInteger":
                                            String varValuebi = v.getInitializer().get().asObjectCreationExpr().getArguments().get(0).asStringLiteralExpr().getValue();

                                            v.setInitializer(new NameExpr("BigInteger.valueOf("+varValuebi+")"));
                                            break;


                                    }
                                    MessageDisplay.finishFixingMessage(documentationURI);
                                    isFound.set(true);

                                }



                            });
                        }


                    }
                });
            });
        });
        return isFound.get();
    }

}
