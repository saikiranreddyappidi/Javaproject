package LabPrograms;

import java.util.Scanner;

import static java.sql.DriverManager.println;


public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        for(int i=1;i<11;i++){
            println(num+"*");
        }
    }
}
