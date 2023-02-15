package locks.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

class ClickNumber {
    int number = 0;

    public synchronized void add() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }


    LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);

    public void clickByLongAccumulator() {
        accumulator.accumulate(1);
    }
}

/**
 * 需求：50个线程每个线程100w次，总点赞数出来
 */
public class AccumulatorCompareDemo {

    public final static int _1W = 10000;
    public final static int THREAD_NUM = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;
        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUM);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM;i++){
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++){
                        clickNumber.add();
                    }
                }finally {
                    countDownLatch1.countDown();
                }
            },String.valueOf(i)).start();
        }
        endTime = System.currentTimeMillis();
        countDownLatch1.await();
        System.out.println(Thread.currentThread().getName() +clickNumber.number+ "\tsynchronize时间消耗："+(endTime-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM;i++){
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++){
                        clickNumber.clickByAtomicLong();
                    }
                }finally {
                    countDownLatch2.countDown();
                }
            },String.valueOf(i)).start();
        }
        endTime = System.currentTimeMillis();
        countDownLatch2.await();
        System.out.println(Thread.currentThread().getName() +clickNumber.atomicLong.get()+ "\tclickByLongAdder时间消耗："+(endTime-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM;i++){
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++){
                        clickNumber.clickByLongAdder();
                    }
                }finally {
                    countDownLatch3.countDown();
                }
            },String.valueOf(i)).start();
        }
        endTime = System.currentTimeMillis();
        countDownLatch3.await();
        System.out.println(Thread.currentThread().getName() +clickNumber.longAdder.sum()+ "\tclickByLongAdder时间消耗："+(endTime-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM;i++){
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++){
                        clickNumber.clickByLongAccumulator();
                    }
                }finally {
                    countDownLatch4.countDown();
                }
            },String.valueOf(i)).start();
        }
        endTime = System.currentTimeMillis();
        countDownLatch4.await();
        System.out.println(Thread.currentThread().getName() +clickNumber.accumulator.get()+ "\tclickByLongAccumulator时间消耗："+(endTime-startTime));
    }
}
