package sycnhronizesup;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedUpDemo {
    public static void main(String[] args) {
        //biased
        Object o = new Object();
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    private static void nolock() {
        Object o = new Object();
        System.out.println("10进制："+o.hashCode());
        System.out.println("16进制："+Integer.toHexString(o.hashCode()));
        System.out.println("2进制："+Integer.toBinaryString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
