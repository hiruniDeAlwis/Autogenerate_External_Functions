package org.wso2.ei.b7a;
import java.lang.reflect.*;
import java.lang.Class.*;
import java.io.IOException;
import java.io.FileWriter;

public class App {
    static String classFullName;
    private static final String HEADER = "import ballerinax/java";
    private static final String FUNCTION ="function";
    private static final String SPACE = " ";
    private static final String BRACKETS = "()";
    private static final String LEFTBRACKET = "(";
    private static final String RIGHTBRACKET = ")";
    private static final String LEFTBRACE = "{";
    private static final String RIGHTBRACE = "}";
    private static final String COMMA = ",";
    private static final String RETURN= "returns handle ";
    private static final String JAVAMETHOD = " = @java:Method";
    private static final String METHODNAME = "name : ";
    private static final String CLASSNAME = "class : ";
    private static final String QUOTE = "\"";
    private static final String EXTERNAL_KEYWORD = "external";
    private static final String SEMICOLON = ";";
    private static final String NEW_LINE = "\n";


    public static void main(String...args){
        TestJava obj = new TestJava();
        Class cls = obj.getClass();
        classFullName=giveFullClass(cls);
        writeMethod(cls,classFullName);
    }

    public static String giveFullClass(Class cls) {

        classFullName=cls.getName();
        return classFullName;
    }

    public static String giveSimpleClass(Class cls) {

        String simpleName = cls.getSimpleName();
        return simpleName;
    }

    public static String writeMethod(Class cls,String classFullName){


        try {
            FileWriter wObj = new FileWriter("../Bal_Project/src/module_bal/external_functions.bal");
            wObj.write(HEADER+SEMICOLON+NEW_LINE);
            Method[] methods = cls.getDeclaredMethods();
            for (Method method:methods) {
                wObj.write(FUNCTION+SPACE);
                wObj.write(method.getName());

                Parameter[] parameter = method.getParameters();
                if (parameter == null) {
                    wObj.write(BRACKETS);
                }
                else{
                    wObj.write(LEFTBRACKET);
                    for(int i=0;i<parameter.length;i++){
                        wObj.write(parameter[i].getParameterizedType() + " " + parameter[i].getName());
                        if(i<parameter.length-1){
                            wObj.write(COMMA);
                        }
                    }
                    wObj.write(RIGHTBRACKET);
                }
                Class returnParam = method.getReturnType();
                String returnType=returnParam.getName();
                if( returnType!= "void"){
                    wObj.write(RETURN);
                }
                wObj.write(JAVAMETHOD+LEFTBRACE+NEW_LINE+METHODNAME+QUOTE);
                wObj.write(method.getName()+QUOTE+COMMA+NEW_LINE);
                wObj.write(CLASSNAME+QUOTE);
                wObj.write(classFullName);
                wObj.write(QUOTE+NEW_LINE+RIGHTBRACE+EXTERNAL_KEYWORD+SEMICOLON);
                wObj.write(NEW_LINE);

            }
            wObj.close();


        }
        catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

        return null;
    }

}