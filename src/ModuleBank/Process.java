package ModuleBank;


class Signals{
    void greenSignal() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+" is passing the track.Waiting...");
        Thread.sleep(300);
        System.out.println(Thread.currentThread().getName()+" passed from the track\n-------------\n");
    }
}


record Trains(Signals train) implements Runnable {
    Trains(Signals train) {
        this.train = train;
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(this, "Train 00" + i);
            t.start();
        }
    }

    @Override
    public void run() {
        synchronized (train) {
            try {
                train.greenSignal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


public class Process {
    public static void main(String []args){
        Signals train = new Signals();
        new Trains(train);
    }
}
