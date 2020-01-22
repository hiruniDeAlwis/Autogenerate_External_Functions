import ballerina/io;
import ballerinax/java;

# Prints `Hello World`.

public function main() {
   io:println("hello");
   var x= printText3(25,1000);
   var useConstructor=TestJava3(java:fromString("Hi"));
   string? text = java:toString(useConstructor);
   io:println(text);
}
