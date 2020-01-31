# Autogenerate_External_Functions
Autogenerate Ballerina External Functions for a Jar



Using jarEntry method in java.util.jar class the classes in the jar will be retrieved. 
UrlClassLoader has been used to load the classes using their fully qualified class name. 
Reflection method has been used to get the details of each class. 
Using write method of FileWriter class external functions will be written on the external_functions.bal file.

Need to give the path to the jar as an argument to method 'getClassDetailsFromJar'.
Path to the external_functions file should be given in the writeToFile method. 
By running the App.java class ballerina external functions for the java classes in the jar would be created in the external_functions file.
