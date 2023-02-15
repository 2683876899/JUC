package locks.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyNumber{
    public AtomicInteger number = new AtomicInteger();

    public void addPlusPlus(){
        number.getAndIncrement();
    }
}

public class AtomicIntegerDemo {

    public final static int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0;i<50;i++){
            new Thread(() -> {
                try {
                    for (int j=0;j<1000;j++){
                        myNumber.addPlusPlus();
                    }
                }finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }
        //等待上面50个线程全部执行完毕，获取数据
        /*try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t"+myNumber.number.get());
    }
}
