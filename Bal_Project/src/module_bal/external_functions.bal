import ballerinax/java;

function printText123() = @java:Method{
name : "printText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function printText23(handle arg0) = @java:Method{
name : "printText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function printText3(handle arg0,int arg1) = @java:Method{
name : "printText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function printText(handle arg0,handle arg1) = @java:Method{
name : "printText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function getText(handle arg0) = @java:Method{
name : "getText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function concatinateText(handle arg0,handle arg1) returns handle  = @java:Method{
name : "concatinateText",
class : "org.wso2.ei.b7a.TestJava"
}external;

function maxByte(byte arg0,byte arg1) returns byte  = @java:Method{
name : "maxByte",
class : "org.wso2.ei.b7a.TestJava"
}external;

function multiplyInt(int arg0,int arg1) returns int  = @java:Method{
name : "multiplyInt",
class : "org.wso2.ei.b7a.TestJava"
}external;

function subtractFloat(float arg0,float arg1) returns float  = @java:Method{
name : "subtractFloat",
class : "org.wso2.ei.b7a.TestJava"
}external;

function divideDouble(float arg0,float arg1) returns float  = @java:Method{
name : "divideDouble",
class : "org.wso2.ei.b7a.TestJava"
}external;

function subtractLong(int arg0,int arg1) returns int  = @java:Method{
name : "subtractLong",
class : "org.wso2.ei.b7a.TestJava"
}external;

function maxOfIntArray(handle arg0) returns int  = @java:Method{
name : "maxOfIntArray",
class : "org.wso2.ei.b7a.TestJava"
}external;

function checkIfTrue(boolean arg0) returns boolean  = @java:Method{
name : "checkIfTrue",
class : "org.wso2.ei.b7a.TestJava"
}external;

function getShort(int arg0) returns int  = @java:Method{
name : "getShort",
class : "org.wso2.ei.b7a.TestJava"
}external;

function TestJava(int arg0) returns handle = @java:Constructor{
class : "org.wso2.ei.b7a.TestJava",
paramTypes : ["int"]
}external;

function TestJava1() returns handle = @java:Constructor{
class : "org.wso2.ei.b7a.TestJava",
paramTypes : [""]
}external;

function TestJava2(int arg0,int arg1) returns handle = @java:Constructor{
class : "org.wso2.ei.b7a.TestJava",
paramTypes : ["int,int"]
}external;

function TestJava3(handle arg0) returns handle = @java:Constructor{
class : "org.wso2.ei.b7a.TestJava",
paramTypes : ["java.lang.String"]
}external;

