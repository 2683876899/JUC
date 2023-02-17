package threadlocal;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class MyObject{


    //这个方法一般不用重写，只是为了演示案例做说明
    @Override
    protected void finalize() throws Throwable {
        // 通常目的是在对象被不可撤销地丢弃之前执行清理操作
        System.out.println("---- invoke finalize");
    }
}
public class ReferenceDemo {
    public static void main(String[] args) {
        // -Xms10m -Xmx10m
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject,referenceQueue);
        List<byte[]> list = new ArrayList<>();
        System.out.println(phantomReference.get());
        new Thread(() -> {
            while (true){
                list.add(new byte[1024 * 1024]);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get()+"\t"+"list add ok");
            }
        },"t1").start();


        new Thread(() -> {
            while (true){
                Reference<? extends MyObject> queue = referenceQueue.poll();
                if (queue != null){
                    System.out.println("----- 有虚对象回收加入了队列");
                    break;
                }
            }
        },"t2").start();
    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("-----gc before 内存够用 :"+weakReference.get());
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("-----gc after 内存够用 :"+weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        //System.out.println("-----softReference:"+softReference.get());
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("-----gc after 内存够用 :"+softReference.get());
        try {
            byte[] bytes = new byte[20 * 1024 * 1024]; //20M对象
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("-----gc after 内存不够用 :"+softReference.get());
        }
    }

    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:"+myObject);

        myObject = null;
        System.gc();
        System.out.println("gc after"+myObject);
    }
}
