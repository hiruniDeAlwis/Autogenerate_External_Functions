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
    private static final String PARAMTYPE = "paramTypes : ";

    public static void main(String[] args) {

        String JarPath="//home/hiruni/javaLibrary/target/javaLibrary-1.0-SNAPSHOT.jar";
        getClassNamesFromJar(JarPath);

    }

    public static List<String>  getClassNamesFromJar(String JarName) {


        try {
            String classFullName=null;
            String method = null;
            int num = 0;
            String consFullName=null;
            String consSimpleName=null;
            List<String> list = new ArrayList<>();

            JarInputStream JarFile = new JarInputStream(new FileInputStream(JarName));
            JarEntry Jar;

            URL[] classLoaderUrls = new URL[]{new URL("file:/"+JarName)};
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

            list.add(HEADER + SEMICOLON + NEW_LINE + NEW_LINE);

            while (true) {
                Jar = JarFile.getNextJarEntry();

                if (Jar == null) {
                    break;
                }

                if ((Jar.getName().endsWith(".class"))) {

                    String className = Jar.getName().replaceAll("/", "\\.");
                    classFullName = className.substring(0, className.lastIndexOf('.'));

                    Class<?> cls = urlClassLoader.loadClass(classFullName);                                                                                                //get class object for a given class

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

                        if (methods[a].isVarArgs() == true) {                                                                                                                //checking for vaarargs type
                            list.add(LEFTBRACKET+"int...values"+RIGHTBRACKET+SPACE+RETURN+"handle");
                        }
                        else {

                            Class<?>[] parameter = methods[a].getParameterTypes();                                                                                               //checking parameter types of a method

                            if (parameter == null) {
                                list.add(BRACKETS);
                            } else {
                                list.add(LEFTBRACKET);
                                for (int i = 0; i < parameter.length; i++) {                                                                                                   //if paraType = int/long/short --> ballerina int
                                    Parameter[] parameterName = methods[a].getParameters();
                                    if (parameter[i].getSimpleName() == "int" | parameter[i].getSimpleName() == "long" | parameter[i].getSimpleName() == "short") {

                                        list.add("int" + SPACE + parameterName[i].getName());
                                        if (i < parameter.length - 1) {

                                            list.add(COMMA);
                                        }
                                    } else if (parameter[i].getSimpleName() == "float" | parameter[i].getSimpleName() == "double") {                                           //if paraType = float/double --> ballerina float

                                        list.add("float" + SPACE + parameterName[i].getName());
                                        if (i < parameter.length - 1) {

                                            list.add(COMMA);
                                        }
                                    } else if (parameter[i].getSimpleName() == "byte") {                                                                                      //if paraType = byte --> ballerina byte

                                        list.add("byte" + SPACE + parameterName[i].getName());
                                        if (i < parameter.length - 1) {

                                            list.add(COMMA);
                                        }
                                    } else if (parameter[i].getSimpleName() == "boolean") {                                                                                  //if paraType = boolean --> ballerina boolean

                                        list.add("boolean" + SPACE + parameterName[i].getName());
                                        if (i < parameter.length - 1) {

                                            list.add(COMMA);
                                        }
                                    } else if (parameter[i].getSimpleName() == "char" || parameter[i].getName() == "java.lang.String") {                                    //if paraType = char or String --> ballerina string

                                        list.add("string" + SPACE + parameterName[i].getName());
                                        if (i < parameter.length - 1) {

                                            list.add(COMMA);
                                        }
                                    } else {
                                        list.add("handle" + SPACE + parameterName[i].getName());                                                                            //other types to ballerina handle
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

                                if ((returnType == "int") | (returnType == "long") | (returnType == "short"))                               //if returnType = int/lon/short --> ballerina int
                                    list.add(SPACE + RETURN + "int ");

                                else if ((returnType == "float") | (returnType == "double"))                                                //if returnType = float/double --> ballerina float
                                    list.add(SPACE + RETURN + "float ");

                                else if ((returnType == "byte"))                                                                           //if returnType = byte --> ballerina byte
                                    list.add(SPACE + RETURN + "byte ");

                                else if ((returnType == "boolean"))                                                                        //if returnType = boolean --> ballerina boolean
                                    list.add(SPACE + RETURN + "boolean ");

                                else if ((returnType == "java.lang.String") || (returnType == "char"))                                    //if returnType = String/char --> ballerina String
                                    list.add(SPACE + RETURN + "string ");

                                else {
                                    list.add(SPACE + RETURN + "handle ");                                                                 //for other reuturnTypes --> ballerina handle
                                }
                            }
                        }
                        list.add(JAVAMETHOD + LEFTBRACE + NEW_LINE + METHODNAME + QUOTE + methods[a].getName() + QUOTE + COMMA + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE);
                        Class<?>[] parameter = methods[a].getParameterTypes();
                        for (Method methodss : methods) {
                            if (methods[a].getName() == methodss.getName()) {
                                list.add(COMMA + NEW_LINE + PARAMTYPE + LEFT_SQUARE_BRACKET);
                                for (int d = 0; d < parameter.length; d++) {
                                    list.add(QUOTE + parameter[d].getName() + QUOTE);
                                    if (d < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                }
                                list.add(RIGHT_SQUARE_BRACKET );
                            }

                            break;
                        }
                        list.add( NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);
                    }
                    Constructor[] constructors = cls.getConstructors();

                    for (int j = 0; j < constructors.length; j++){

                        consFullName=constructors[j].getName();
                        int firstChar=consFullName.lastIndexOf ('.') + 1;                                                   //get the simple name of constructor
                        if ( firstChar>0) {
                            consSimpleName = consFullName.substring(firstChar);
                        }

                        if (j == 0) {
                            list.add(FUNCTION + SPACE+consSimpleName);

                        } else {
                            list.add(FUNCTION + SPACE + consSimpleName + j);
                        }
                        Class<?>[] parameter = constructors[j].getParameterTypes();

                        if (parameter == null) {
                            list.add(BRACKETS);
                        } else {
                            list.add(LEFTBRACKET);
                            for (int i = 0; i < parameter.length; i++) {
                                Parameter[] parameterName = constructors[j].getParameters();
                                if (parameter[i].getSimpleName() == "int" | parameter[i].getSimpleName() == "long" | parameter[i].getSimpleName() == "short") {       //if paraType = int/long/short --> ballerina int

                                    list.add("int" + SPACE + parameterName[i].getName());
                                    if (i < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                } else if (parameter[i].getSimpleName() == "float" | parameter[i].getSimpleName() == "double") {                                      //if paraType = float/double --> ballerina float

                                    list.add("float" + SPACE + parameterName[i].getName());
                                    if (i < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                } else if (parameter[i].getSimpleName() == "byte") {                                                                                 //if paraType = byte --> ballerina byte

                                    list.add("byte" + SPACE + parameterName[i].getName());
                                    if (i < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                } else if (parameter[i].getSimpleName() == "boolean") {                                                                             //if paraType = boolean --> ballerina boolean

                                    list.add("boolean" + SPACE + parameterName[i].getName());
                                    if (i < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                } else if (parameter[i].getSimpleName() == "char"||parameter[i].getName()=="java.lang.String") {                                    //if paraType = char or String --> ballerina string

                                    list.add("string" + SPACE + parameterName[i].getName());
                                    if (i < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                }else {
                                    list.add("handle" + SPACE + parameterName[i].getName());                                                                      //other types to ballerina handle
                                    if (i < parameter.length - 1) {

                                        list.add(COMMA);
                                    }
                                }

                            }
                            list.add(RIGHTBRACKET);
                        }
                        list.add(SPACE + RETURN_FOR_CONSTRUCTOR + LEFTBRACE + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE + COMMA + NEW_LINE);
                        Class<?>[] param = constructors[j].getParameterTypes();

                        if (param == null) {

                        } else {
                            list.add(PARAMETER_TYPE_NAME + LEFT_SQUARE_BRACKET);
                            for (int i = 0; i < param.length; i++) {
                                Parameter[] paramName = constructors[j].getParameters();
                                list.add(QUOTE + param[i].getName() + QUOTE);
                                if (i < param.length - 1) {

                                    list.add(COMMA);
                                }
                            }
                            list.add(RIGHT_SQUARE_BRACKET + NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);
                        }
                    }
                }
                getStream(list);
                writeToFile(list);
            }
        } catch (Exception e) {
                System.out.println("Oops.. Encounter an issue while parsing jar " + e.toString());
            }

        return null;
        }

        public static <T> void getStream(List<T> list) {
            Stream<T> stream = list.stream();
            Iterator<T> it = stream.iterator();
            while (it.hasNext()) {
                System.out.print(it.next() + " ");
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