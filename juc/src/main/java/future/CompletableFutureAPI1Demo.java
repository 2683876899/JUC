package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 1获得结果和触发计算
 * 1.1 获得结果
 * public T get() 阻塞获取
 * public T get(long timeout,TimeUnit unit)
 * public T join() 和get类似
 * public T getNow(T valueIfAbsent)
 * 没有计算完成的情况下，给我一个替代结果
 * 立即获取结果不阻塞，计算完返回计算完的结果，没计算完返回设定的valueIfAbsent
 * 1.2主动触发计算
 * public boolean  complete(T value)是否打断get方法立刻返回括号值
 *
 */
public class CompletableFutureAPI1Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task";
        });
        //System.out.println(completableFuture.get());
        //System.out.println(completableFuture.get(1,TimeUnit.SECONDS));
       // System.out.println(completableFuture.join());

        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //System.out.println(completableFuture.getNow("xxx"));
        System.out.println(completableFuture.complete("sss") + "\t "+ completableFuture.join());


    }
}
