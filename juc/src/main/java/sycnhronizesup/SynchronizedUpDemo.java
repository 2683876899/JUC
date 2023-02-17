package sycnhronizesup;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class SynchronizedUpDemo {
    public static void main(String[] args) {

        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //biased
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("==============================");
        new Thread(() -> {
            synchronized (o){
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t1").start();

    }

    private static void nolock() {
        Object o = new Object();
        System.out.println("10进制："+o.hashCode());
        System.out.println("16进制："+Integer.toHexString(o.hashCode()));
        System.out.println("2进制："+Integer.toBinaryString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
