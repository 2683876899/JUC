package locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {

    static volatile boolean isStop = false;

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName() + "\t 监测到Interrupted()变为true，中断线程");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "\t is come in Interrupted()");
            }
        }, "t1");
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // t2向t1发出协商，将t1的中断标志位设为true希望t1停下来
        new Thread(t1::interrupt,"t2").start();
        // // t1自己中断
        // t1.interrupt();

    }

    private static void m2ToAtomicBoolean() {
        new Thread(() -> {
            while (true){
                if (atomicBoolean.get()){
                    System.out.println(Thread.currentThread().getName() + "\t 监测到atomicBoolean变为true，中断线程");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "\t is come in atomicBoolean");
            }
        },"t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(() -> {
            atomicBoolean.set(true);
        },"t2").start();
    }

    private static void m1ToVolatile() {
        new Thread(() -> {
            while (true){
                if (isStop){
                    System.out.println(Thread.currentThread().getName() + "\t 监测到isStop变为true，中断线程");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "\t is come in volatile");
            }
        },"t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        },"t2").start();
    }
}
