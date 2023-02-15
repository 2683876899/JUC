package future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 1.get容易导致堵塞，一般建议放在程序最后面。一旦调用必须等待任务执行结果，不管你计算是否完成，容易造成程序堵塞
 * 2.假如我不愿意等待很长时间，我希望过时不候，可以自动离开(FutureTask提供get(long timeout, TimeUnit unit)方法)
 * 3.使用isDone()方法轮询判断任务是否完成
 * 结论：Future对于结果获取不友好，只能通过阻塞或轮询的方式得到任务结果
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println(Thread.currentThread().getName() + " \t ------come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "task over";
        });
        Thread thread = new Thread(futureTask,"t1");
        thread.start();

       // System.out.println(futureTask.get());

        System.out.println(Thread.currentThread().getName() + "\t ----忙其他任务了");
        //System.out.println(futureTask.get(3,TimeUnit.SECONDS));
        while (true){
            if (futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("轮询等待任务执行完成！" );
            }
        }
    }
}
