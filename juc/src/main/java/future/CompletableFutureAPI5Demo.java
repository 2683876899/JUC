package future;

import java.util.concurrent.CompletableFuture;

/**
 * 5.对计算结果进行合并
 * 两个CompletionStage任务都完成后，最终能把两个任务的结果一起交给thenCombine来处理
 * 先完成的先等着，等待其他分支任务
 */
public class CompletableFutureAPI5Demo {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----- 1");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----- 2");
            return 20;
        }), Integer::sum).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----- 3");
            return 30;
        }), Integer::sum);

        System.out.println(Thread.currentThread().getName()+"\t 合并的结果为" + completableFuture.join());
    }
}
