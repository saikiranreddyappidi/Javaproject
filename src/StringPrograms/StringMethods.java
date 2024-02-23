package StringPrograms;

import java.util.Scanner;
import java.util.StringTokenizer;

public class StringMethods {
    static String arr="1+2*3";
    public static void main(){
        ans();
//        Scanner read = new Scanner(System.in);
//        System.out.print("Enter a string : ");
//        StringTokenizer string = new StringTokenizer(read.nextLine(), ".",false);
//        System.out.println(string.countTokens());
//        while(string.hasMoreTokens()){
//            System.out.println(string.nextToken());
        }
    public static String ans() {
        String[] arra = arr.split("[+\\-*/]");
        int[] num = new int[arra.length];
        for(int i=0; i<arra.length; i++){
            num[i] = Integer.parseInt(arra[i]);
        }
        float result = 0;
        for(int i=0; i<arr.length(); i++){
            if(arr.charAt(i)=='+'){
                result = num[0]+num[1];
            }
            else if(arr.charAt(i)=='-'){
                result = num[0]-num[1];
            }
            else if(arr.charAt(i)=='*'){
                result = num[0]*num[1];
            }
            else if(arr.charAt(i)=='/'){
                result = (float)num[0]/(float) num[1];
            }
        }
        System.out.println(result);
        return Float.toString(result);
    }
}
