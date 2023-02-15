package locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReEntryLockDemo {

    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + "\t come in m1");
        m2();
        System.out.println(Thread.currentThread().getName() + "\t end m1");
    }

    public synchronized void m2(){
        System.out.println(Thread.currentThread().getName() + "\t come in m2");
        m3();
    }

    public synchronized void m3(){
        System.out.println(Thread.currentThread().getName() + "\t come in m3");
    }

    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        /*// 同步方法
        ReEntryLockDemo reEntryLockDemo = new ReEntryLockDemo();
        new Thread(() -> {reEntryLockDemo.m1();},"a").start();*/
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t  come in 外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "\t  come in 内层调用");
                }finally {
                    lock.unlock();
                }
            }finally {
               // lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+ "\t come in t2");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }


    /**
     * 同步代码块
     */
    private static void reEntryM1() {
        final Object o = new Object();
        new Thread(() -> {
            synchronized (o){
                System.out.println(Thread.currentThread().getName()+"\t 外层调用");
                synchronized (o){
                    System.out.println(Thread.currentThread().getName()+"\t 中层调用");
                    synchronized (o){
                        System.out.println(Thread.currentThread().getName()+"\t 内层调用");
                    }
                }
            }
        },"a").start();
    }
}
