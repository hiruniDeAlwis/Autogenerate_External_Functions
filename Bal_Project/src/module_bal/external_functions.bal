import ballerinax/java;

function getText1(string arg0,int arg1) = @java:Method{
name : "getText",
class : "org.wso2.ei.TestOne",
paramTypes : ["java.lang.String","int"]
}external;

function getText(string arg0) = @java:Method{
name : "getText",
class : "org.wso2.ei.TestOne",
paramTypes : ["java.lang.String"]
}external;

function concatinateText(string arg0,string arg1) returns string  = @java:Method{
name : "concatinateText",
class : "org.wso2.ei.TestOne"
}external;

function maxByte(byte arg0,byte arg1) returns byte  = @java:Method{
name : "maxByte",
class : "org.wso2.ei.TestOne"
}external;

function multiplyInt(int arg0,int arg1) returns int  = @java:Method{
name : "multiplyInt",
class : "org.wso2.ei.TestOne"
}external;

function subtractFloat(float arg0,float arg1) returns float  = @java:Method{
name : "subtractFloat",
class : "org.wso2.ei.TestOne"
}external;

function divideDouble(float arg0,float arg1) returns float  = @java:Method{
name : "divideDouble",
class : "org.wso2.ei.TestOne"
}external;

function subtractLong(int arg0,int arg1) returns int  = @java:Method{
name : "subtractLong",
class : "org.wso2.ei.TestOne"
}external;

function checkIfTrue(boolean arg0,byte arg1) returns boolean  = @java:Method{
name : "checkIfTrue",
class : "org.wso2.ei.TestOne"
}external;

function maxOfIntArray10(int arg0,handle arg1) returns int  = @java:Method{
name : "maxOfIntArray",
class : "org.wso2.ei.TestOne"
}external;

function maxOfIntArray(handle arg0) returns handle  = @java:Method{
name : "maxOfIntArray",
class : "org.wso2.ei.TestOne"
}external;

function getShort(int arg0) returns int  = @java:Method{
name : "getShort",
class : "org.wso2.ei.TestOne"
}external;

function printText1(string arg0,string arg1) = @java:Method{
name : "printText",
class : "org.wso2.ei.TestTwo",
paramTypes : ["java.lang.String","java.lang.String"]
}external;

function printText2(string arg0) = @java:Method{
name : "printText",
class : "org.wso2.ei.TestTwo",
paramTypes : ["java.lang.String"]
}external;

function printText3(int arg0,int arg1) returns int  = @java:Method{
name : "printText",
class : "org.wso2.ei.TestTwo",
paramTypes : ["int","int"]
}external;

function printText() returns string  = @java:Method{
name : "printText",
class : "org.wso2.ei.TestTwo",
paramTypes : []
}external;

function stringArray(handle arg0) returns handle  = @java:Method{
name : "stringArray",
class : "org.wso2.ei.TestTwo"
}external;

function TestOne(string arg0) returns handle = @java:Constructor{
class : "org.wso2.ei.TestOne",
paramTypes : ["java.lang.String"]
}external;

function TestOne1(int arg0) returns handle = @java:Constructor{
class : "org.wso2.ei.TestOne",
paramTypes : ["int"]
}external;

function TestOne2() returns handle = @java:Constructor{
class : "org.wso2.ei.TestOne",
paramTypes : []
}external;

function TestOne3(int arg0,int arg1) returns handle = @java:Constructor{
class : "org.wso2.ei.TestOne",
paramTypes : ["int","int"]
}external;

