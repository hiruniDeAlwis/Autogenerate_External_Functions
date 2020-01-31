package org.wso2.ei.b7a;
import java.util.*;
import java.util.stream.Stream;
import java.lang.reflect.*;
import java.lang.Class.*;
import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;

public class App {

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
    private static final String PARAMTYPE = "paramTypes : ";


    public static void main(String...args){

        String JarPath="//home/hiruni/javaLibrary/target/javaLibrary-1.0-SNAPSHOT.jar";
        getClassNamesFromJar(JarPath);
    }

    public static String[] getClassNamesFromJar(String JarName) throws NullPointerException{
        try {

            List<String> listOfClasses = new ArrayList<>();
            JarInputStream JarFile = new JarInputStream(new FileInputStream(JarName));
            JarEntry Jar;

            while (true) {
                Jar = JarFile.getNextJarEntry();

                if (Jar == null) {
                    break;
                }

                if ((Jar.getName().endsWith(".class"))) {
                    String className = Jar.getName().replaceAll("/", "\\.");
                    listOfClasses.add(className);
                }
            }

            String[] arrayofClasses=new String[listOfClasses.size()];
            arrayofClasses = listOfClasses.toArray(arrayofClasses);
            writeToList(arrayofClasses,JarName);
        }
        catch (Exception e){
            System.out.println("Encounted an issue while parsing jar.. "+e.toString());
        }

        return null;
    }

    public static List<String> writeToList(String[] arrayOfClasses,String JarName) {

        try {
            List<String> list = new ArrayList<>();
            list.add(HEADER + SEMICOLON + NEW_LINE + NEW_LINE);

            for(String className:arrayOfClasses){

                System.out.println("--"+className);
                String classFullName = className.substring(0, className.lastIndexOf('.'));
                String method = null;                                                                                                                                  //suffix for overloaded methods

                URL[] classLoaderUrls = new URL[]{new URL("file:/" + JarName)};
                URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
                Class<?> cls = urlClassLoader.loadClass(classFullName);                                                                                                //loads given class of the jar

                Method[] methods = cls.getDeclaredMethods();                                                                                                             //get declared methods of a class

                for (int a = 0; a < methods.length; a++) {

                    list.add(FUNCTION + SPACE + methods[a].getName());

                    for (int b = a + 1; b < methods.length; b++) {                                                                                                       //checking for methods with same name
                        if (methods[a].getName() == methods[b].getName()) {
                            method = "" + b;
                            list.add(method);                                                                                                                            //adding a suffix to methods with same name
                        }
                        break;                                                                                                                                           //if a method is found with same name, break and go to next method
                    }

                    if (methods[a].isVarArgs() == true) {
                        list.add(LEFTBRACKET + "int...values" + RIGHTBRACKET + SPACE + RETURN + "handle");                                                               //need to futher implement for overlaoding java varargs by giving element class type and dimension
                    } else {

                        Class<?>[] parameter = methods[a].getParameterTypes();                                                                                               //checking parameter types of a method

                        if (parameter == null) {
                            list.add(BRACKETS);
                        } else {
                            list.add(LEFTBRACKET);
                            for (int i = 0; i < parameter.length; i++) {
                                Parameter[] parameterName = methods[a].getParameters();
                                list.add(mapParameters(parameter[i].getName()) + SPACE + parameterName[i].getName());
                                if (i < parameter.length - 1) {
                                    list.add(COMMA);
                                }
                            }
                        }
                        list.add(RIGHTBRACKET);
                    }

                    Class returnParam = methods[a].getReturnType();
                    String returnType = returnParam.getName();
                    list.add(mapReturnType(returnType));                                                                                                                         //add return type if not void

                    Class<?>[] parameter = methods[a].getParameterTypes();
                    list.add(JAVAMETHOD + LEFTBRACE + NEW_LINE + METHODNAME + QUOTE + methods[a].getName() + QUOTE + COMMA + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE);
                    for (Method methodss : methods) {
                        if (methods[a].getName() == methodss.getName()) {
                            list.add(COMMA + NEW_LINE + PARAMTYPE + LEFT_SQUARE_BRACKET);
                            for (int d = 0; d < parameter.length; d++) {
                                list.add(QUOTE + parameter[d].getName() + QUOTE);
                                if (d < parameter.length - 1) {

                                    list.add(COMMA);
                                }
                            }
                            list.add(RIGHT_SQUARE_BRACKET + NEW_LINE);
                        }

                        break;
                    }
                    list.add(NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);
                }

                Constructor[] constructors = cls.getConstructors();                                                                                     //get the constructor array
                for (int j = 0; j < constructors.length; j++) {                                                                                         //if only one constructor just add constructor name
                    if (j == 0) {
                        list.add(FUNCTION + SPACE + giveSimpleName(constructors[j].getName()));
                    } else {                                                                                                                            //if more than one constructor add a suffix to constructor name
                        list.add(FUNCTION + SPACE + giveSimpleName(constructors[j].getName()) + j);
                    }
                    Class<?>[] parameter = constructors[j].getParameterTypes();                                                                         //get parameter array of constructor

                    if (parameter == null) {
                        list.add(BRACKETS);
                    } else {
                        list.add(LEFTBRACKET);
                        for (int i = 0; i < parameter.length; i++) {
                            Parameter[] parameterName = constructors[j].getParameters();
                            list.add(mapParameters(parameter[i].getName()) + SPACE + parameterName[i].getName());                                        //(parametrType parameterName)
                            if (i < parameter.length - 1) {
                                list.add(COMMA);                                                                                                         //add a comma if there is more than parameter
                            }
                        }

                    }
                    list.add(RIGHTBRACKET);

                    list.add(SPACE + RETURN_FOR_CONSTRUCTOR + LEFTBRACE + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE + COMMA + NEW_LINE);      //java consttructors has no returnType but ballerina external function returns handle for constructors

                    if (parameter == null) {

                    } else {
                        list.add(PARAMETER_TYPE_NAME + LEFT_SQUARE_BRACKET);
                        for (int i = 0; i < parameter.length; i++) {
                            Parameter[] paramName = constructors[j].getParameters();
                            list.add(QUOTE + parameter[i].getName() + QUOTE);                                                                               //if there is a parameter give the parameter type within square brackets
                            if (i < parameter.length - 1) {
                                list.add(COMMA);
                            }
                        }
                        list.add(RIGHT_SQUARE_BRACKET + NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);
                    }
                    getStream(list);                                                                                                                    //write the list to stream
                    writeToFile(list);                                                                                                                  //write the list to external_functions.bal file
                }
            }

        } catch (Exception e) {
            System.out.println("error when creating list.." + e.toString());
        }
        return null;
    }

    public static String giveSimpleName(String ClassFullName){
        String classFullName=null;
        String className=null;
        int firstChar=classFullName.lastIndexOf ('.') + 1;
        if ( firstChar>0) {
            className = classFullName.substring(firstChar);
        }
        return className;
    }

    public static String mapReturnType(String returnType){

        if (returnType != "void") {

            if ((returnType == "int") | (returnType == "long") | (returnType == "short"))                                   //if return type int/long or short --> ballerina int type
                returnType= SPACE+RETURN+"int ";

            else if ((returnType == "float") | (returnType == "double"))                                                    //if return type float/double --> ballerina float type
                returnType= SPACE+RETURN+"float ";

            else if ((returnType == "byte"))                                                                                //if return type byte --> ballerina byte
                returnType= SPACE+RETURN+"byte ";

            else if ((returnType == "boolean"))                                                                            //if return type boolean --> ballerina boolean
                returnType= SPACE+RETURN+"boolean ";

            else if ((returnType == "java.lang.String") || (returnType == "char"))                                         //if return type String --> ballerina string
                returnType= SPACE+RETURN+"string ";

            else {
                returnType= SPACE+RETURN+"handle ";                                                                        //for other return types --> ballerina handle
            }
        }
        else{
            returnType="";                                                                                                 //for void type methods --> no return keyword for external function
        }
        return returnType;
    }

    public static String mapParameters(String parameterType){
        if (parameterType == "int" || parameterType == "long" || parameterType == "short") {                               //if parameter type int/long or short --> ballerina int type

            parameterType="int";

        } else if (parameterType == "float" || parameterType == "double") {                                                 //if parameter type float/double --> ballerina float type

            parameterType="float";

        } else if (parameterType == "byte") {                                                                               //if parameter type byte --> ballerina byte

            parameterType="byte";

        } else if (parameterType == "boolean") {                                                                            //if return type boolean --> ballerina boolean

            parameterType="boolean";

        } else if (parameterType == "char" || parameterType == "java.lang.String") {                                         //if parameter type String --> ballerina string

            parameterType="string";

        } else {
            parameterType="handle";                                                                                             //for other parameter types ---> ballerina handle

        }
        return parameterType;

    }

    public static <T> void getStream(List<T> list) {
        Stream<T> stream = list.stream();
        Iterator<T> it = stream.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + SPACE);                                                                             //writes the given list to output stream
        }
    }

    public static String writeToFile(List<String> list) {
        try {
            FileWriter wObj = new FileWriter("../Bal_Project/src/module_bal/external_functions.bal");                       //writes the given list to external_functions.bal
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