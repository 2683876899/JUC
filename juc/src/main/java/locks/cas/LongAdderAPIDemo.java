package locks.cas;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderAPIDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.sum());

        LongAccumulator accumulator = new LongAccumulator((x,y) -> x * y,1);
        accumulator.accumulate(2);
        accumulator.accumulate(3);
        System.out.println(accumulator.get());
    }
}
