package locks.volatiles;

import java.util.concurrent.TimeUnit;

public class VolatileSeeDemo {

    static volatile boolean flag = true;
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----- come in");
            while (flag){

            }
            System.out.println(Thread.currentThread().getName() + "flag值被修改结束");
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        flag = false;
        System.out.println(Thread.currentThread().getName() + "\t 修改flag值");
    }
}
