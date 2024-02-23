package LabPrograms;/*A small school with only 100 lockers has this ritual on the last day of school:
 The students go into the hall and stand by their closed lockers. At the first blow of a whistle,
 the students open every locker. At the second whistle, the students close every second locker (lockers 2,4,6 etc. are slammed shut).
 At the third whistle, the students toggle every third locker. To "toggle" means to close if it's open, and to open if it's closed.
 They toggle lockers 3,6,9 etc.to 99. At whistle four, they toggle every fourth locker.
 At whistle five, they toggle every fifth locker and so on. At the hundredth whistle, the students standing next to
 locker 100 (and only that student) toggle his locker. Write a Java code to implement the same and display the results for
 all whistles.
*/


import java.util.Objects;

public class Students {
    static String[] toggle(String []array, int n){
        for(int i=1;i<=100;i++){
            if(i%n==0){
                if(Objects.equals(array[i], "closed")){
                    array[i]="open";
                }
                else{
                    array[i]="closed";
                }
            }
        }
        display(array,n);
        if(n!=100){
            return toggle(array,n+1);
        }
        return array;
    }
    static void display(String []array,int n){
        System.out.println("\nWhistle: "+n);
        System.out.print("Opened: {");
        for(int i=1;i<=100;i++) {
            if(Objects.equals(array[i], "open")){
                System.out.print(i+",");
            }
        }System.out.print("}");
        System.out.print("\nClosed: {");
        for(int i=1;i<=100;i++) {
            if(Objects.equals(array[i], "closed")){
                System.out.print(i+",");
            }
        }System.out.print("}");
    }

    public static void main(String []args){
        String[] locker;
        locker = new String[110];
        //whistle 1
        for(int i=1;i<=100;i++){
            locker[i]="open";
        }
        display(locker,1);
        toggle(locker,2);
    }
}
