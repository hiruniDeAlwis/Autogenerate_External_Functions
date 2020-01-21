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
    private static final String RETURN= "returns ";
    private static final String JAVAMETHOD = " = @java:Method";
    private static final String METHODNAME = "name : ";
    private static final String CLASSNAME = "class : ";
    private static final String PARAMETER_TYPE_NAME = "paramTypes : ";
    private static final String QUOTE = "\"";
    private static final String EXTERNAL_KEYWORD = "external";
    private static final String SEMICOLON = ";";
    private static final String NEW_LINE = "\n";
    private static final String RETURN_FOR_CONSTRUCTOR= "returns handle = @java:Constructor";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "]";


    public static void main(String...args){

        TestJava obj = new TestJava();
        Class cls = obj.getClass();
        classFullName=giveClass(cls);
        List<String> list=writeToList(cls,classFullName);
        writeToFile(list);
    }

    public static String giveClass(Class cls) {

        classFullName = cls.getName();
        return classFullName;
    }

    public static String giveSimpleName(String ClassFullName){
        String className=null;
        int firstChar=classFullName.lastIndexOf ('.') + 1;
        if ( firstChar>0) {
            className = classFullName.substring(firstChar);
        }
        return className;
    }


    public static List<String> writeToList(Class cls, String classFullName) {
        String method = null;
        int num=0;
        List<String> list = new ArrayList<>();

        list.add(HEADER + SEMICOLON + NEW_LINE + NEW_LINE);

        Method[] methods = cls.getDeclaredMethods();

        for (int a = 0; a < methods.length; a++) {
            list.add(FUNCTION + SPACE + methods[a].getName());

            for (int b = a + 1; b < methods.length; b++) {
                if (methods[a].getName() == methods[b].getName()) {
                    num=a+1;
                    method = "" + num;
                    list.add(method);
                }
                break;
            }
            Class<?>[] parameter = methods[a].getParameterTypes();

            if (parameter == null) {
                list.add(BRACKETS);
            }
            else {
                list.add(LEFTBRACKET);
                for (int i = 0; i < parameter.length; i++) {
                    Parameter[] parameterName = methods[a].getParameters();
                    if (parameter[i].getSimpleName() == "int" | parameter[i].getSimpleName() == "long" | parameter[i].getSimpleName() == "short") {

                        list.add("int" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }
                    else if (parameter[i].getSimpleName() == "float" | parameter[i].getSimpleName() == "double") {

                        list.add("float" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }
                    else if (parameter[i].getSimpleName() == "byte") {

                        list.add("byte" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }
                    else if (parameter[i].getSimpleName() == "boolean") {

                        list.add("boolean" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }
                    else {
                        list.add("handle" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }

                }
                list.add(RIGHTBRACKET);
            }

            Class returnParam = methods[a].getReturnType();
            String returnType = returnParam.getName();

            if (returnType != "void") {

                if ((returnType == "int") | (returnType == "long") | (returnType == "short"))
                    list.add(SPACE + RETURN + "int ");

                else if ((returnType == "float") | (returnType == "double"))
                    list.add(SPACE + RETURN + "float ");

                else if ((returnType == "byte"))
                    list.add(SPACE + RETURN + "byte ");

                else if ((returnType == "boolean"))
                    list.add(SPACE + RETURN + "boolean ");

                else {
                    list.add(SPACE + RETURN + "handle ");
                }
            }
            list.add(JAVAMETHOD + LEFTBRACE + NEW_LINE + METHODNAME + QUOTE + methods[a].getName() + QUOTE + COMMA + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE + NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);
        }
//.......................................................................................................................................................................................................................................

        Constructor[] constructors = cls.getConstructors();
        for (int j=0; j<constructors.length;j++) {
            if(j==0) {
                list.add(FUNCTION + SPACE + giveSimpleName(constructors[j].getName()));
            }
            else {
                list.add(FUNCTION + SPACE + giveSimpleName(constructors[j].getName())+j);
            }
            Class<?>[] parameter = constructors[j].getParameterTypes();

            if (parameter == null) {
                list.add(BRACKETS);
            }
            else {
                list.add(LEFTBRACKET);
                for (int i = 0; i < parameter.length; i++) {
                    Parameter[] parameterName = constructors[j].getParameters();
                    if (parameter[i].getSimpleName() == "int" | parameter[i].getSimpleName() == "long" | parameter[i].getSimpleName() == "short") {

                        list.add("int" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }
                    else if (parameter[i].getSimpleName() == "float" | parameter[i].getSimpleName() == "double") {

                        list.add("float" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    } else if (parameter[i].getSimpleName() == "byte") {

                        list.add("byte" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    } else if (parameter[i].getSimpleName() == "boolean") {

                        list.add("boolean" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    } else {
                        list.add("handle" + SPACE + parameterName[i].getName());
                        if (i < parameter.length - 1) {

                            list.add(COMMA);
                        }
                    }

                }
                list.add(RIGHTBRACKET);
            }
            list.add(SPACE + RETURN_FOR_CONSTRUCTOR + LEFTBRACE + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE + COMMA + NEW_LINE );
            Class<?>[] param = constructors[j].getParameterTypes();

            if (param == null) {

            } else {
                list.add(PARAMETER_TYPE_NAME + LEFT_SQUARE_BRACKET+ QUOTE);
                for (int i = 0; i < param.length; i++) {
                    Parameter[] paramName = constructors[j].getParameters();
                    list.add(param[i].getName());
                    if (i < param.length - 1) {

                        list.add(COMMA);
                    }
                    }
                    list.add(QUOTE + RIGHT_SQUARE_BRACKET + NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);
                }
                getStream(list);
            }
        return list;

    }

    public static <T> void getStream(List<T> list) {
        Stream<T> stream = list.stream();
        Iterator<T> it = stream.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + SPACE);
        }
    }

    public static String writeToFile(List<String> list) {
        try {
            FileWriter wObj = new FileWriter("../Bal_Project/src/module_bal/external_functions.bal");
            for (String temp : list) {
                wObj.write(temp);
            }
            wObj.close();

        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return null;
    }
}