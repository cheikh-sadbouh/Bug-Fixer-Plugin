package com.upm;

import com.github.javaparser.JavaParser;
import com.github.javaparser.JavaParserBuild;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.upm.detector.BugMajorDetector;
import com.upm.detector.CodeSmellMajorDetector;
import com.upm.detector.CodeSmellMinorDetector;
import com.upm.fixer.CodeSmellMajorFixer;
import com.upm.fixer.CodeSmellMinorFixer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class ClassAstOperation  {




 /*   private static String text =" class me{ \n" +
           // "        BigDecimal bd2 = new BigDecimal(\"1.1\"); \n" +
            "        BigDecimal bd2 = new BigDecimal(1.1); \n" +

            "    }";*/

  /*  private static String text =" class me{ \n" +
            "List<String> collection1 = Collections.EMPTY_LIST;  \n" +
            "Map<String, String> collection2 = Collections.EMPTY_MAP;  \n" +
            "Set<String> collection3 = Collections.EMPTY_SET;  "+

            "}";
*/

/*
    private static String text =" class me{ \n" +
            "void fun(){"+
        "if (myCollection.size() == 0) {   \n" +

            "    }"+
            "    }"+

            "}";

*/

/*    private static String text =" class me{ \n" +
            "void fun(){"+
        //  "String empty = new String(); \n" +
            "String nonempty = new String(\"Hello world\");" +
            "Double myDouble = new Double(1.1); \n" +
            "Integer integer = new Integer(1); \n" +
            "Boolean bool = new Boolean(true); \n" +
            "BigInteger bigInteger1 = new BigInteger(\"3\");\n" +
            "BigInteger bigInteger2 = new BigInteger(\"9223372036854775807\"); \n" +
            "BigInteger bigInteger3 = new BigInteger(\"111222333444555666777888999\"); "+
            "    }"+
            "}";*/
/*

    private static String text =" class me{ \n" +
            "void fun(){"+
            "if( myString.equals(\"foo\")){  "+
            "System.out.print(\"data\"); "+
            "System.out.print(\"data\"); "+
            "System.out.print(\"data\"); "+
            "a++;"+
            "   } }"+
            "}";
*/

/*    private static String text =" class me{ \n" +
            "void fun(){"+
        "String firstName = getFirstName(); // String overrides equals\n" +
          "String lastName = getLastName();\n" +
          "\n" +
          "if (firstName == lastName) {  };"+
            "    }"+
            "}";*/

/*
   private static String text =" class me{ \n" +
            "void fun(){" +
          "String str = null;" +
            "for (int i = 0; i < arrayOfStrings.length ; ++i) {\n" +
            "  str = str + arrayOfStrings[i];\n" +
            "}"+
            "} } ";

*/
/*

    private static String text =" class me{ \n" +
            "void fun(){" +
           "    String myNum = \"12.2\";\n" +
            "\n" +
            "    float f = (new Float(myNum)).floatValue();"+
            "} } ";
*/


 /*   private static String text =" class me{ \n" +
            "void fun(){" +

            "} } ";*/


    /*
    private static String text =" class me{ \n" +
            "void fun(){" +
            "call(\"meme\");"+
            "call(\"meme\");"+
            "call(\"meme\");"+
            ""+
            "           System.out.println(\"hello\");\n"+
            "           System.out.println(\"fjdlfjl\");\n"+
            "           System.out.println(\"fjdlfjl\");\n"+
            "           System.out.println(\"hello\");\n"+
            "           System.out.println(\"vvv\");\n"+
            "           System.out.println(\"hello\");\n"+
            "} } ";
*/

    private static String text =" class me{ \n" +
            "void fun(){" +
            " String myStr = \"Hello World\";\n" +
            "    // ...\n" +
            "    int pos = myStr.indexOf(\"9\");  // Noncompliant\n" +
            "    int pos = myStr.indexOf(\'8\');  // Noncompliant\n" +
            "    // ...\n" +
            "    int otherPosl = myStr.lastIndexOf(\"v\"); }} // Noncompliant";
        public static void main(String[] args) throws IOException
    {
        CompilationUnit originalCu = JavaParser.parse(text);
      //  CompilationUnit cu = LexicalPreservingPrinter.setup(originalCu);
       // System.out.println(cu.toString());


        YamlPrinter printer = new YamlPrinter(true);
        System.out.println(printer.output(originalCu));
        CodeSmellMinorDetector.RSPEC_3027(originalCu);


        //CodeSmellMinorDetector.RSPEC_1186(cu);
      //  CodeSmellMinorDetector.RSPEC_2130(cu);
       // CodeSmellMinorDetector.RSPEC_1643(cu);
       // CodeSmellMinorDetector.RSPEC_4973(cu);
       // CodeSmellMinorDetector.RSPEC_1132(cu);

       // CodeSmellMajorDetector.RSPEC_2129(cu);
       // CodeSmellMinorDetector.RSPEC_1155(cu);

      //  CodeSmellMinorDetector.RSPEC_1596(cu);
        //BugMajorDetector.RSPEC_2111(cu);
      //  CriticalBugDetector.RSPEC_1143(cu);

      //  CriticalBugDetector.RSPEC_1175(cu);
       // Files.delete(path);

       // CodeSmellMajorFixer.RSPEC_3042_FIX(cu);
    //    System.out.println(cu.toString());
      //  CodeSmellMajorDetector.RSPEC_3042(cu);








    }

    private static void transform(ClassOrInterfaceDeclaration c)
    {
        List<MethodDeclaration> methods = c.getMethods();
        for (MethodDeclaration method : methods) {
            //c.remove(method);
            System.out.println(method.getDeclarationAsString(true,true));
        }

        c.getExtendedTypes().forEach(f->{
            System.out.println(f.getName());
        });
    }
}
