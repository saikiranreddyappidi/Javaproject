package MultiThreading;

import java.util.Timer;
import java.util.TimerTask;

class Reminder {
    Timer timer;
    public boolean up=false;

    public Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds* 1000L);
    }

    class RemindTask extends TimerTask {
        public void run() {
            System.out.println("Time's up!");
            timer.cancel(); //Terminate the timer thread
        }
    }

}

class sample implements Runnable{

    @Override
    public void run() {
        System.out.println("Starting");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class timers {
    public static void main(String[] args) {
        Reminder reminder = new Reminder(5);
        System.out.println("Task scheduled.");
        while (reminder.up){
            new Thread(new sample()).start();
        }
    }
}

