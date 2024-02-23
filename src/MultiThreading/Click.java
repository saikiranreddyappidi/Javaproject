package MultiThreading;

class OnClick implements Runnable{
    Thread t;
    long clicking;
    public volatile boolean c=true;
    OnClick(){
        t=new Thread(this);
//        t.start();
    }
    public void run(){
        while (c){
            clicking++;
        }
    }
    public void start(){
        t.start();
    }
    public void stop(){
        c=false;
    }
}

public class Click {
    public static void main(String []args) throws InterruptedException {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        OnClick high=new OnClick();
        OnClick low=new OnClick();
        high.t.setPriority(Thread.NORM_PRIORITY+2);
        low.t.setPriority(Thread.NORM_PRIORITY-4);
        low.start();
        high.start();
        Thread.sleep(100);
        low.stop();
        high.stop();
        high.t.join();
        low.t.join();
        System.out.println(high.clicking);
        System.out.println(low.clicking);
    }
}
