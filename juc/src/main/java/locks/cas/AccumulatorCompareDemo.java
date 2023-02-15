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

    public static void main(String[] args) {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;
        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUM);

    }
}
