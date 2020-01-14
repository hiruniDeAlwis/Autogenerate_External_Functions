package org.wso2.ei.b7a;

public class TestJava {
    public static void printText() {

        System.out.println("Hello World!");
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

    public static int maxOfIntArray(int[] myarray){

        int maximum=0;
        for(int i=0; i<myarray.length; i++ ) {
            if(myarray[i]>maximum) {
                maximum = myarray[i];
            }
        }
        return maximum;
    }

    public static boolean checkIfTrue(boolean x) {

        if(x==true){
            return true;
        }

        else{
            return false;
        }
    }
}