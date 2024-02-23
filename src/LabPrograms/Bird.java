package LabPrograms;

class Animal{
    public void walk(){
        System.out.println("Walk");
    }
}

public class Bird extends Animal{
    public void fly() {
        System.out.println("Fly");
    }

    public static void main(String []args){
        Bird obj=new Bird();
        obj.walk();
        obj.fly();
    }

}
