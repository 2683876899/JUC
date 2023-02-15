package locks;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {
        Object objA = new Object();
        Object objB = new Object();

        new Thread(() -> {
            synchronized (objA){
                System.out.println(Thread.currentThread().getName()+ "成功获取A锁，尝试获取B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (objB){
                    System.out.println(Thread.currentThread().getName()+ "成功获取B锁");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (objB){
                System.out.println(Thread.currentThread().getName()+ "成功获取B锁，尝试获取A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (objA){
                    System.out.println(Thread.currentThread().getName()+ "成功获取A锁");
                }
            }
        },"B").start();
    }
}
