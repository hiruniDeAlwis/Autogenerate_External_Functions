package org.wso2.ei.b7a;

public class TestJava {

    int age;
    int amount;
    String name;

    public TestJava(){

    }

    public TestJava(int age){
        this.age=age;
    }

    public TestJava(String name){

        this.name=name;
    }

    public TestJava(int age,int amount){
        this.age=age;
        this.amount=amount;
    }

    public static void printText() {

        System.out.println("Hello World!");
    }

    public static void printText(String msg) {

        System.out.println("Hello World!"+msg);
    }

    public static void printText(String msg1,String msg2) {

        System.out.println("1st message: "+msg1);
        System.out.println("2nd message: "+msg2);
    }

    public static int printText(int age,int amount) {

        System.out.println("age: "+age);
        return age+5;
    }

    public static void getText(String name) {

        System.out.println(name);
    }

    public static String concatinateText(String a, String b){

        return a+b;
    }

    public static short getShort(short x) {

        return x;
    }

    public static byte maxByte(byte a,byte b) {
        byte max = a;
        if(b>a){
            max=b;
        }
        return max;
    }

    public static int multiplyInt(int x, int y) {

        int mul = x*y;
        return mul;
    }

    public static float subtractFloat(float x, float y) {

        return x-y;
    }

    public static double divideDouble(float x, float y) {

        return x/y;
    }

    public static long subtractLong(long x, long y) {

        long sub = x - y;
        return sub;
    }

    public static int maxOfIntArray(int maximum,int[] myarray){

        for(int i=0; i<myarray.length; i++ ) {
            if(myarray[i]>maximum) {
                maximum = myarray[i];
            }
        }
        return maximum;
    }

    public static int[] maxOfIntArray(int[] myarray){

        return myarray;
    }

    public static String[] stringArray(String[] myarray){

        return myarray;
    }

    public static boolean checkIfTrue(boolean x) {

        if(x==true){
            return true;
        }

        else{
            return false;
        }
    }

    public static void varargsCheck(String...args){
        System.out.println("hello world!");
    }
}