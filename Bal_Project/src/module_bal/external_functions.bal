import ballerinax/java; 
function printText()  = @java:Method { name: " printText ", class : " org.wso2.ei.b7a.TestJava" } 
external; 
function add() returns handle = @java:Method { name: " add ", class : " org.wso2.ei.b7a.TestJava" } 
external; 
