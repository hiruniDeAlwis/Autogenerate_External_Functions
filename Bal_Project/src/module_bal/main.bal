import ballerina/io;

public function main() {
   io:println("hello");
   var x= printText(25,1000);
   var y=printText1("First","second");
   var useConstructor=TestJava1("Hi");
   //string? text = java:toString(useConstructor);
   //io:println(text);
   //var z=getText("Hiruni");
   io:println(concatinateText("De ","Alwis"));
}
