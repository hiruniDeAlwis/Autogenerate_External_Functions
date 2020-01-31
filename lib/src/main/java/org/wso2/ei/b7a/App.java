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


    public static void main(String...args){

        String JarName="//home/hiruni/javaLibrary/target/javaLibrary-1.0-SNAPSHOT.jar";                                                   //path for the java library jar should be given here
        getClassDetailsFromJar(JarName);
    }

    public static void getClassDetailsFromJar(String JarName){
        try {
            String method=null;                                                                                                             //suffix for overloading methods
            List<String> listOfClasses = new ArrayList<>();                                                                                 //list of class names in the jar
            List<String> list = new ArrayList<>();                                                                                          //list to which class details are added
            list.add(HEADER + SEMICOLON + NEW_LINE + NEW_LINE);                                                                             //importing ballerinax/java to external_functions.bal file
            JarInputStream JarFile = new JarInputStream(new FileInputStream(JarName));                                                      //getting jar entry from given jar
            JarEntry Jar;
            URL[] classLoaderUrls = new URL[]{new URL("file:/" + JarName)};                                                                  //giving jar name from which classes are to be loaded
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

            while (true) {
                Jar = JarFile.getNextJarEntry();                                                                                            //going through the jar entries

                if (Jar == null) {
                    break;
                }

                if ((Jar.getName().endsWith(".class"))) {
                    String className = Jar.getName().replaceAll("/", "\\.");                                                                //removing the .class extension from the class name
                    listOfClasses.add(className);                                                                                           //adding the classnames to a list
                }
            }

            String[] arrayofClasses = new String[listOfClasses.size()];
            arrayofClasses = listOfClasses.toArray(arrayofClasses);                                                                       //coverting the list with names of classes to an array

            for (int y=0;y<arrayofClasses.length;y++) {                                                                                   //looping through each class name to get method details of the class
                System.out.println(arrayofClasses[y]+"[class no: "+y+"]");                                                                //print the class names

                String classFName=arrayofClasses[y];                                                                                        //assinging name of class to the string classFName

                String classFullName = classFName.substring(0, classFName.lastIndexOf('.'));

                Class<?> cls = urlClassLoader.loadClass(classFullName);                                                                     //load each class by class name

                Method[] methods = cls.getDeclaredMethods();                                                                                //get the declared methods in methods array

                for (int a = 0; a < methods.length; a++) {                                                                                  //looping thriugh the method array
                    list.add(FUNCTION + SPACE + methods[a].getName());

                    for (int b = a + 1; b < methods.length; b++) {
                        if (methods[a].getName() == methods[b].getName()) {                                                                //adding a suffix to the end of method name if for methods with similar method names
                            method = "" + b;
                            list.add(method);
                        }
                        break;
                    }

                    Class<?>[] parameter = methods[a].getParameterTypes();                                                                //getting the parameter types of a method

                    if (parameter == null) {                                                                                              //if no parameters put brackets
                        list.add(BRACKETS);
                    } else {
                        list.add(LEFTBRACKET);
                        Parameter[] parameterName = methods[a].getParameters();                                                          //creating array of parameter names of a method
                        for (int i = 0; i < parameter.length; i++) {                                                                     //looping through array of parameters and mapping them to ballerina types
                            if (parameter[i].getSimpleName() == "int" | parameter[i].getSimpleName() == "long" | parameter[i].getSimpleName() == "short") {

                                list.add("int" + SPACE + parameterName[i].getName());
                                if (i < parameter.length - 1) {

                                    list.add(COMMA);
                                }
                            } else if (parameter[i].getSimpleName() == "float" | parameter[i].getSimpleName() == "double") {

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
                            }else if (parameter[i].getSimpleName() == "char"||parameter[i].getName()=="java.lang.String") {

                                list.add("string" + SPACE + parameterName[i].getName());
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

                    Class returnParam = methods[a].getReturnType();                                                              //getting return type for each method
                    String returnType = returnParam.getName();                                                                   //assining return type to String return type

                    if (returnType != "void") {                                                                                  //mapping return type to ballerina types

                        if ((returnType == "int") | (returnType == "long") | (returnType == "short"))
                            list.add(SPACE + RETURN + "int ");

                        else if ((returnType == "float") | (returnType == "double"))
                            list.add(SPACE + RETURN + "float ");

                        else if ((returnType == "byte"))
                            list.add(SPACE + RETURN + "byte ");

                        else if ((returnType == "boolean"))
                            list.add(SPACE + RETURN + "boolean ");

                        else if ((returnType == "java.lang.String")||(returnType=="char"))
                            list.add(SPACE + RETURN + "string ");

                        else {
                            list.add(SPACE + RETURN + "handle ");
                        }
                    }
                    list.add(JAVAMETHOD + LEFTBRACE + NEW_LINE + METHODNAME + QUOTE + methods[a].getName() + QUOTE + COMMA + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE);    //adding method name and class name inside the external function
                    for (Method methodss : methods) {
                        if (methods[a].getName() == methodss.getName()) {                                                                                                              //checking for methods with similar names and adding parameter types of those methods to the external function
                            list.add(COMMA + NEW_LINE + PARAMTYPE + LEFT_SQUARE_BRACKET);
                            for (int d = 0; d < parameter.length; d++) {
                                list.add(QUOTE + parameter[d].getName() + QUOTE);                                                                                                     //Ex:- ParamType : ["type1, type2"]
                                if (d < parameter.length - 1) {

                                    list.add(COMMA);
                                }
                            }
                            list.add(RIGHT_SQUARE_BRACKET );
                        }

                        break;
                    }
                    list.add(NEW_LINE + RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);                                            //ending extrnal function with external keyword
                }

            }
            for(int z=0;z<arrayofClasses.length;z++){                                                                                               //going through the array of classes

                String classFullName = arrayofClasses[z].substring(0, arrayofClasses[z].lastIndexOf('.'));                                          //get class name without .class extension

                Class<?> cls = urlClassLoader.loadClass(classFullName);                                                                            //load the zth class
                String  consName=null;                                                                                                             //declaring simple name of constructor

                Constructor[] constructors = cls.getConstructors();                                                                                //get the constructer array for a classs
                for (int j = 0; j < constructors.length; j++) {

                    String consFullName=constructors[j].getName();                                                                                //getting name of constructor
                    int firstchar=consFullName.lastIndexOf ('.')+1;
                    if(firstchar>0){
                        consName=consFullName.substring(firstchar);                                                                               //getting the simple name of constructor
                    }

                    System.out.println(consName+" no:"+j);                                                                                        //print the constructor name

                    if (j == 0) {
                        list.add(FUNCTION + SPACE + consName);                                                                                    //if just one constructor add the name of constructor
                    } else {
                        list.add(FUNCTION + SPACE + consName + j);                                                                               //for multipple constructors add a suffix to the name
                    }
                    Class<?>[] parameter = constructors[j].getParameterTypes();                                                                  //get the parameter type array of a constructor

                    if (parameter == null) {
                        list.add(BRACKETS);
                    } else {
                        list.add(LEFTBRACKET);
                        for (int i = 0; i < parameter.length; i++) {                                                                             //loop through parameter arrray and map them to ballerina types
                            Parameter[] parameterName = constructors[j].getParameters();
                            if (parameter[i].getSimpleName() == "int" | parameter[i].getSimpleName() == "long" | parameter[i].getSimpleName() == "short") {

                                list.add("int" + SPACE + parameterName[i].getName());
                                if (i < parameter.length - 1) {

                                    list.add(COMMA);
                                }
                            } else if (parameter[i].getSimpleName() == "float" | parameter[i].getSimpleName() == "double") {

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
                            } else if (parameter[i].getSimpleName() == "char"||parameter[i].getName()=="java.lang.String") {

                                list.add("string" + SPACE + parameterName[i].getName());
                                if (i < parameter.length - 1) {

                                    list.add(COMMA);
                                }
                            }else {
                                list.add("handle" + SPACE + parameterName[i].getName());
                                if (i < parameter.length - 1) {

                                    list.add(COMMA);
                                }
                            }

                        }
                        list.add(RIGHTBRACKET);
                    }
                    list.add(SPACE + RETURN_FOR_CONSTRUCTOR + LEFTBRACE + NEW_LINE + CLASSNAME + QUOTE + classFullName + QUOTE + COMMA + NEW_LINE);                 //add class name and parameter types inside the external function for a constructor
                    Class<?>[] param = constructors[j].getParameterTypes();

                    if (param == null) {

                    } else {
                        list.add(PARAMETER_TYPE_NAME + LEFT_SQUARE_BRACKET);
                        for (int i = 0; i < param.length; i++) {
                            Parameter[] paramName = constructors[j].getParameters();                                                                               //Ex:- ParamType: ["int,String]
                            list.add(QUOTE + param[i].getName() + QUOTE);
                            if (i < param.length - 1) {

                                list.add(COMMA);
                            }
                        }
                        list.add(RIGHT_SQUARE_BRACKET + NEW_LINE);
                    }
                    list.add( RIGHTBRACE + EXTERNAL_KEYWORD + SEMICOLON + NEW_LINE + NEW_LINE);                                                                  //end the external function for a constructor with external keyword

                }

            }
            getStream(list);                                                                                                                                    //stream the list to standard output
            writeToFile(list);                                                                                                                                  //write the list on given file name
        }
        catch (Exception e){
            System.out.println("Encounted an issue while parsing jar.. "+e.toString());
        }

    }


    public static String writeToFile(List<String> list) {
        try {
            FileWriter wObj = new FileWriter("../Bal_Project/src/module_bal/external_functions.bal");                                               //give the path to the external_functions ballerina file here
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

    public static <T> void getStream(List<T> list) {                                                                                             //taking an array list and streaming to standard output
        Stream<T> stream = list.stream();
        Iterator<T> it = stream.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + SPACE);
        }
    }
}