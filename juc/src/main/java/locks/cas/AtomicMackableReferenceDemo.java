package locks.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * CAS --> Unsafe --> do while + ABA --> AtomicStampedReference,AtomicMackableReference
 * AtomicStampedReference,version号，+1
 * AtomicMackableReference，一次，false，true
 */
public class AtomicMackableReferenceDemo {

    static AtomicMarkableReference<Integer> markableReference = new AtomicMarkableReference<>(100,false);
    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"默认标志位："+marked);
            // 等待线程2拿到相同的标志位
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            markableReference.compareAndSet(100,1000,marked,!marked);
        },"t1").start();

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"默认标志位："+marked);
            //等待线程1执行完成
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            boolean b = markableReference.compareAndSet(100, 2023, marked, !marked);
            System.out.println(Thread.currentThread().getName()+"线程CASresult："+b);
            System.out.println(Thread.currentThread().getName()+"\t"+markableReference.isMarked());
            System.out.println(Thread.currentThread().getName()+"\t"+markableReference.getReference());
        },"t2").start();
    }
}
