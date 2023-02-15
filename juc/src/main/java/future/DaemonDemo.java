package future;

import java.util.concurrent.TimeUnit;

// 守护线程测试
public class DaemonDemo {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始运行，" +
                    (Thread.currentThread().isDaemon() ? "守护线程":"用户线程"));
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " \t  -----end主线程" );
    }
}
