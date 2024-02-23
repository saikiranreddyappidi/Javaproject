package ModuleBank;/*. A party of four travelers comes to a rickety bridge at night. The bridge can hold the weight of at most two
of the travelers at a time, and it cannot be crossed without using a flashlight. The travelers have one flashlight among them.
Each traveler walks at a different speed: The first can cross the bridge in 1 minute, the second in 2 minutes,
the third in 5 minutes, and the fourth takes 10 minutes to cross the bridge. If two travelers cross together,
they walk at the speed of the slower traveler. Generate a Java program to find the least amount of time in which
all the travelers can cross from one side of the bridge to the other? */

import java.util.Arrays;

public class FourTravelers {
    public static void main(String []args){
        int []minutes = {2, 1, 5, 10, 12};
        Arrays.sort(minutes);
        int count=0;
        for(int i=1;i<minutes.length;i+=2){
            count+=minutes[i];
        }
        count+=minutes[minutes.length-1];
        System.out.println(count+" "+minutes.length);
    }
}
