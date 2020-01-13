import ballerinax/java;

function concatinateText(handle arg0,handle arg1) returns handle  = @java:Method{
name : "concatinateText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function getText(handle arg0) = @java:Method{
name : "getText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function printText() = @java:Method{
name : "printText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function add(int arg0,int arg1) returns handle  = @java:Method{
name : "add",
class : "org.wso2.ei.b7a.TestJava"
}external;

