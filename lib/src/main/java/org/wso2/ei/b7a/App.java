package org.wso2.ei.b7a;
import java.util.*;
import java.util.stream.Stream;
import java.lang.reflect.*;
import java.lang.Class.*;
import java.io.IOException;
import java.io.FileWriter;

public class App {
    private static String classFullName;
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

            List<String> list = new ArrayList<>();
            list.add(HEADER+SEMICOLON+NEW_LINE+NEW_LINE);

            Method[] methods = cls.getDeclaredMethods();
            for (Method method:methods) {
                list.add(FUNCTION+SPACE+method.getName());

                Class<?>[] parameter = method.getParameterTypes();
                if (parameter == null) {
                    list.add(BRACKETS);
                }
                else{
                    list.add(LEFTBRACKET);
                    for(int i=0;i<parameter.length;i++) {
                        Parameter[] parameterName = method.getParameters();
                        if(parameter[i].getSimpleName()=="int") {
                            list.add(parameter[i].getSimpleName() + SPACE + parameterName[i].getName());
                            if(i<parameter.length-1){
                                list.add(COMMA);
                            }
                        }
                        else{
                            list.add("handle" + SPACE + parameterName[i].getName());
                            if(i<parameter.length-1){
                                list.add(COMMA);
                            }
                        }

                    }
                    list.add(RIGHTBRACKET);
                }

                Class returnParam = method.getReturnType();
                String returnType=returnParam.getName();
                if( returnType!= "void"){
                    list.add(SPACE+RETURN);
                }
                list.add(JAVAMETHOD+LEFTBRACE+NEW_LINE+METHODNAME+QUOTE+method.getName()+QUOTE+COMMA+NEW_LINE+CLASSNAME+QUOTE+classFullName+QUOTE+NEW_LINE+RIGHTBRACE+EXTERNAL_KEYWORD+SEMICOLON+NEW_LINE+NEW_LINE);
            }
            getStream(list);

        return null;
    }

    public static <T> void getStream(List<T> list){
        try {
            Stream<T> stream = list.stream();
            Iterator<T> it = stream.iterator();
            while (it.hasNext()) {
                System.out.print(it.next() + SPACE);
            }
        }
        catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
}