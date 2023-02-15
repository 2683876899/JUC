package locks;

import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

public class InterruptDemo1 {
    public static void main(String[] args) {
        // 实例方法Interrupt()仅仅是设置线程的中断状态为true，不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("-----" + i);
            }
            System.out.println("t1线程调用interrupt()后的中断标志02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();
        System.out.println("t1线程的中断标志：" + t1.isInterrupted());
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        t1.interrupt();
        System.out.println("t1线程调用interrupt()后的中断标志01：" + t1.isInterrupted());//true

        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("t1线程调用interrupt()后的中断标志03：" + t1.isInterrupted());//false中断不活动的线程不会产生影响

    }
}
