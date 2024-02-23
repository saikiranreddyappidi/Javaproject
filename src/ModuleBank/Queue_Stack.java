package ModuleBank;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

class QNode {
    int key;
    QNode next;
    public QNode(int key)
    {
        this.key = key;
        this.next = null;
    }
}

class Queue {
    QNode front, rear;
    private long cnt;
    public Queue() { this.front = this.rear = null; }

    void enqueue(int key)
    {
        QNode temp = new QNode(key);
        if (this.rear == null) {
            this.front = this.rear = temp;
            return;
        }
        this.rear.next = temp;
        this.rear = temp;
    }

    void dequeue()
    {
        if (this.front == null)
            return;
        this.front = this.front.next;
        if (this.front == null)
            this.rear = null;
    }

    void printQueue(){
        QNode temp=front;
        System.out.println();
        System.out.print("Queue elements :");
        while(temp!=null){
            System.out.print(temp.key+" ");
            temp=temp.next;
        }
        System.out.println();
    }
    long capacity(){
        QNode temp=front;
        while(temp!=null){
            cnt++;
            temp=temp.next;
        }
        return cnt;
    }
    int value(){
        try{
            return front.key;
        }
        catch (NullPointerException e){
//            System.out.println(e.getMessage());
            return -1;
        }
    }
    boolean isEmpty(){
        return front==null && rear ==null;
    }
}
class Stacks{
    Stack <Integer>stack;
    Stacks(Stack<Integer>stack){
        this.stack=stack;
    }
    void push(int value){
        stack.push(value);
    }
    void pop(){
        try{
            stack.pop();
        }
        catch (EmptyStackException e){
            System.out.println(e.getMessage());
        }
    }

    int value(){
        if(!stack.empty()){
            return stack.peek();
        }
        else{
            return 0;
        }
    }
    void printStack(){
        System.out.println();
        System.out.print("Stack elements: ");
        for(int i:stack){
            System.out.print(i+" ");
        }
        System.out.println();
    }
    boolean isEmpty(){
        return stack.isEmpty();
    }
}
class QueueToStack{
    Stacks stc;
    Queue que;
    long push,pop;
    QueueToStack(Queue que,Stacks stack){
        this.que=que;
        this.stc=stack;
//        shift();
        printing();
    }
    void shift(){
        while(que!=null){
            if(stc.isEmpty()){
                stc.push(que.value());
                push++;
                que.dequeue();
            } else if (stc.value()> que.value()) {
                que.enqueue(stc.value());
                stc.pop();
                pop++;
                shift();
            }
            else {
                stc.push(que.value());
                push++;
                que.dequeue();
            }
            if(stc.value()==-1||que.value()==-1){
                return;
            }
        }
    }
    synchronized void printing(){
        String name=Thread.currentThread().getName();
        System.out.println("----------------------->>>"+name+"<<<-----------------------");
        System.out.print("Name of the current thread: "+name);
        que.printQueue();
        shift();
        stc.printStack();
        System.out.println(Thread.currentThread().getName()+" Push operations: "+push+" Pop operations: "+pop);
    }
}
class NewThread implements Runnable{
    Thread t;
    Random rand=new Random();
    long totalPush,totalPop;
    NewThread() throws InterruptedException {
        for(int i=0;i<10;i++){
            t=new Thread(this, "Thread "+i);
            t.start();
            t.join();
        }
    }
    @Override
    public void run(){
        Queue q = new Queue();
        Stack<Integer> stack=new Stack<>();
        Stacks stc=new Stacks(stack);
        for(int i=1;i<5;i++){
            q.enqueue(rand.nextInt(10));
        }
        QueueToStack q_s = new QueueToStack(q,stc);
        totalPush+= q_s.push;
        totalPop+= q_s.pop;
    }
}
public class Queue_Stack {
    static QueueToStack best;
    static QueueToStack worst;
    public static void main(String[] args) throws InterruptedException {
        NewThread mi = new NewThread();
        mi.t.join();
        Best();
        Worst();
        System.out.println("<<<<<<<<<<<<< Results >>>>>>>>>>>>");
        System.out.println("Best case: \nPush: "+best.push+" Pop: "+best.pop);
        System.out.println("Worst case: \nPush: "+worst.push+" Pop: "+worst.pop);
        System.out.println("Avg pushes: "+mi.totalPush/10+" Avg popes: "+mi.totalPop/10);
    }

    private static void Best() {
        Queue q = new Queue();
        Stack<Integer> stack=new Stack<>();
        Stacks stc=new Stacks(stack);
        for(int i=4;i>0;i--){
            q.enqueue(i);
        }
        best = new QueueToStack(q,stc);
    }
    private static void Worst() {
        Queue q = new Queue();
        Stack<Integer> stack=new Stack<>();
        Stacks stc=new Stacks(stack);
        for(int i=1;i<5;i++){
            q.enqueue(i);
        }
        worst = new QueueToStack(q,stc);
        System.exit(1);
    }
}

