package LabPrograms;

import java.util.Scanner;

class printing{
    public void input(String name){
        System.out.println("Your name is "+name+".");
    }
    static class copying {
        public void age(int ag){
            System.out.println(ag);
        }
    }
}


public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter name : ");
        String name = scan.nextLine();
        printing obj = new printing();
        obj.input(name);
        System.out.println(name);
        printing.copying Age = new printing.copying();
        System.out.print("Enter Age : ");
        int age = scan.nextInt();
        Age.age(age);
    }
}
