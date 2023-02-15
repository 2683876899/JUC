package future;

import jdk.nashorn.internal.ir.ReturnNode;

import java.util.concurrent.CompletableFuture;

/**
 * 3.计算结果进行消费
 * thenAccept 接收任务的处理结果，并消费处理，无返回结果
 * thenRun(Runnable runnable) 任务A执行完执行B，并且B不需要A的结果
 * thenAccept(Consumer consumer) 任务A执行完执行B，B需要A的结果，但是任务B没有返回值
 * thenApply(Function function) 任务A执行完执行B，B需要A的结果，同时B有返回值
 *
 *
 * CompletableFuture和线程池之间的关系
 */
public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply( v -> {
            return v + 1;
        }).thenApply( v -> {
            return v + 1;
        }).thenAccept(System.out::println);
    }
}
