package MultiThreading;

class StoreRoom{
    int i;
    boolean value=false;
    synchronized void get() throws InterruptedException {
        if(!value){
            wait();
        }
        System.out.println("Got: "+i);
        value=false;
        notify();
    }
    synchronized void put(int j) throws InterruptedException {
        if (value){
            wait();
        }
        i=j;
        System.out.println("Put: "+i);
        value=true;
        notify();
    }
}

class Consumer implements Runnable{
    StoreRoom store;
    Thread thre;
    Consumer(StoreRoom str){
        store=str;
        thre=new Thread(this);
        thre.start();
    }

    @Override
    public void run() {
        int i=0;
        while (true){
            try {
                store.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }
}

class Producer implements Runnable{
    StoreRoom store;
    Thread thre;
    Producer(StoreRoom str){
        store=str;
        thre=new Thread(this);
        thre.start();
    }

    @Override
    public void run() {
        int i=0;
       while (true){
           try {
               store.put(i++);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }
    }
}

public class Seller {
    public static void main(String []args){
        StoreRoom store = new StoreRoom();
        new Producer(store);
        new Consumer(store);
    }
}
