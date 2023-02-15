package locks.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 首次获取的版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第二次获取的版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第三次获取的版本号："+atomicStampedReference.getStamp());
        },"t1").start();



        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 首次获取的版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(100, 2023, stamp, stamp + 1));
        },"t2").start();

    }

    private static void ABAhappen() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---come");
            atomicInteger.compareAndSet(100,101);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(101,100);
        },"t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(() -> {
            System.out.println(atomicInteger.compareAndSet(100, 101));
        },"t2").start();
    }
}
