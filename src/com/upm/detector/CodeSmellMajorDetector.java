package com.upm.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.printer.YamlPrinter;
import com.upm.util.MessageDisplay;

import java.util.concurrent.atomic.AtomicReference;

public interface CodeSmellMajorDetector {
    /*
     * Unused "private" classes should be removed
     *
     * @see
     * https://rules.sonarsource.com/java/RSPEC-3985
     *
     * */
    static boolean RSPEC_3985(CompilationUnit cu) {
        final String documentationURI = "java/RSPEC-3985";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> {
            if(c.isInnerClass()) {
               int counter= (int) cu.findAll(SimpleName.class).stream().filter(d -> c.asClassOrInterfaceDeclaration().getName().asString().equals(d.asString())).count();

              if(counter == 1 && c.asClassOrInterfaceDeclaration().getModifiers().toString().equals("[PRIVATE]") ){
                  isFound.set(true);
                  MessageDisplay.ShowMessage(
                          " Unused \"private\" classes should be removed"
                          , c.getBegin().get().line
                          , documentationURI,
                          c.asClassOrInterfaceDeclaration().getName().asString());
              }

            }


        });


        return isFound.get();
    }


    /*
     * "@RequestMapping" methods should be "public"
     *
     * @see
     * https://rules.sonarsource.com/java/RSPEC-3751
     *
     * */
    static boolean RSPEC_3751(CompilationUnit cu) {
        final String documentationURI = "java/RSPEC-3751";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {
            c.getMethods().forEach(m->{
                m.getAnnotations().forEach(annotationExpr -> {

                            if( annotationExpr.toString().contains("@RequestMapping")
                            && !m.getModifiers().contains(Modifier.PUBLIC)){

                                MessageDisplay.ShowMessage(
                                         "@RequestMapping methods should be `public`"
                                        , m.getBody().get().getBegin().get().line
                                        , documentationURI,
                                        c.asClassOrInterfaceDeclaration().getName().asString());
                                isFound.set(true);

                            }
                });
            });



        });


        return isFound.get();
    }

    /*
     * "writeObject" should not be the only "synchronized" code in a class
     *
     * @see
     * https://rules.sonarsource.com/java/RSPEC-3042
     *
     * */
    static boolean RSPEC_3042(CompilationUnit cu) {
        final String documentationURI = "java/RSPEC-3042";
        AtomicReference<Boolean> isFound = new AtomicReference<>(false);
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {

          Long TotalSynchronizedKeyWord= c.getMethods().stream()
                    .filter(m-> m.getModifiers().contains(Modifier.SYNCHRONIZED))
                    .count();

                for (MethodDeclaration method : c.getMethods()) {

                 //   System.out.println(" inout ="+method.getParameters().toString().contains("ObjectOutputStream"));
                 //   System.out.println("has sync = "+method.getModifiers().contains(Modifier.SYNCHRONIZED));
                 //   System.out.println("named = "+ method.getName().asString().equals("writeObject"));
                if(method.getParameters().toString().contains("ObjectOutputStream")
                && method.getModifiers().contains(Modifier.SYNCHRONIZED)
                        && method.getName().asString().equals("writeObject")
                        && TotalSynchronizedKeyWord <= 1
                ){

                    MessageDisplay.ShowMessage(
                            "`writeObject` should not be the only `synchronized` code in a class"
                            , method.getBegin().get().line
                            , documentationURI,
                            c.asClassOrInterfaceDeclaration().getName().asString());

               isFound.set(true);
                }

            }


        });


        return isFound.get();
    }





    /*  Constructors should not be used to instantiate "String", "BigInteger",
         "BigDecimal" and primitive-wrapper classes
     *
     * @see
     * https://rules.sonarsource.com/java/type/Bug/RSPEC-2129
     *
     * */

    static boolean RSPEC_2129(CompilationUnit cu) {
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
                                              MessageDisplay.ShowMessage(
                                                      "Constructors should not be used to instantiate \"String\", \"BigInteger\",\n" +
                                                              "         \"BigDecimal\" and primitive-wrapper classes"
                                                      , v.getBegin().get().line
                                                      , documentationURI,
                                                      c.asClassOrInterfaceDeclaration().getName().asString());

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
