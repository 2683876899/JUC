package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyData{

    ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    public void add(){
        count.set(1+count.get());
    }
}
public class ThreadLocalDemo2 {

    public static void main(String[] args) {
        MyData myData = new MyData();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            for (int i=0;i<10;i++){
                executorService.submit(() -> {
                    try {
                        Integer before = myData.count.get();
                        myData.add();
                        Integer after = myData.count.get();
                        System.out.println(Thread.currentThread().getName() + "\t before:"+before + "\t after:"+after);
                    }finally {
                        myData.count.remove();
                    }
                });
            }
        } finally {
            executorService.shutdown();
        }


    }
}
