package MultiThreading;

class NewThread implements Runnable{
    Thread t;
    String ThreadName;
    NewThread(String ThreadName) throws InterruptedException {
        this.ThreadName=ThreadName;
        t=new Thread(this,ThreadName);
        t.start();
//        t.join();
    }
    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(i+" "+t);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


public class Threading{
    public static void main(String []args) throws InterruptedException {
        NewThread new1=new NewThread("One");
        NewThread new2=new NewThread("Two");
        NewThread new3=new NewThread("Three");
        System.out.println("Thread One is alive: "
                + new1.t.isAlive());
        System.out.println("Thread Two is alive: "
                + new2.t.isAlive());
        System.out.println("Thread Three is alive: "
                + new3.t.isAlive());
        try{
            System.out.println("Waiting....");
            new1.t.join();
            new2.t.join();
            new3.t.join();
        }
        catch (Exception e){
            throw new InterruptedException();
        }
        System.out.println("Completed.");
        System.out.println("Thread One is alive: "
                + new1.t.isAlive());
        System.out.println("Thread Two is alive: "
                + new2.t.isAlive());
        System.out.println("Thread Three is alive: "
                + new3.t.isAlive());
        System.out.println(Thread.currentThread().isAlive());
    }
}