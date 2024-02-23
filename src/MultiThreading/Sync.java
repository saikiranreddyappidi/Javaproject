package MultiThreading;

class callme{
    void calling(String msg) throws InterruptedException {
        System.out.print("[ "+msg);
        Thread.sleep(1000);
        System.out.println(" ]");
    }
}

class caller implements Runnable{
    String msg;
    final callme call;
    Thread th;
    caller(callme callings,String msg){
        call=callings;
        this.msg=msg;
        th=new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        synchronized (call){
            try {
                call.calling(msg);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Sync {
    public static void main(String []args) throws InterruptedException {
        callme call=new callme();
        caller caller1=new caller(call,"hello");
        caller caller2=new caller(call,"world");
        caller caller3=new caller(call,"completed");
        caller1.th.join();
        caller2.th.join();
        caller3.th.join();
    }
}
