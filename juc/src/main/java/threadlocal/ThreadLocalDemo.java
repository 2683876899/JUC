package threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class House{
     int saleCount = 0;

     public synchronized void saleHouse(){
         ++saleCount;
     }

     /*ThreadLocal<Integer> saleVolume = new ThreadLocal<>(){
         @Override
         protected Object initialValue() {
             return 0
         }
     };*/

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void setSaleVolume(){
        saleVolume.set(1 + saleVolume.get());
    }
}

/**
 * 需求1：5个销售卖房子，集团高层只关心销售总量的准确数统计
 * 需求2：5个销售卖完随机数房子，各自独立销售额度，自己业绩按提成走，分灶吃饭，各个销售自己动手，丰衣足食
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();
        for (int i = 0; i<5; i++){
            new Thread(() -> {
                try {
                    int size = new Random().nextInt(6);
                    for (int j = 0;j<size;j++){
                        house.saleHouse();
                        house.setSaleVolume();
                    }
                    //System.out.println(size);
                    System.out.println(Thread.currentThread().getName() + "\t" + house.saleVolume.get());
                }finally {
                    house.saleVolume.remove();
                }
            },String.valueOf(i)).start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t最终销售的总量是："+ house.saleCount);
    }
}
