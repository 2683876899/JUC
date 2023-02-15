package locks.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class BankAccount {
    String bankName = "BBC";

    // 更新的对象熟悉必须使用public，volatile修饰
    public volatile int money;

    public synchronized void add() {
        money++;
    }

    // 因为对象的属性修改类型原子类都是抽象类，所以每次使用必须使用静态方法newUpdater()创建一个更新器，并且传入需要更新的类和属性。
    AtomicIntegerFieldUpdater<BankAccount> accountAtomicIntegerFieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");
    // 不加synchronized，保证高性能原子性，局部微创手术
    public void transMoney(BankAccount bankAccount){
        accountAtomicIntegerFieldUpdater.getAndIncrement(bankAccount);
    }

}

/**
 * 以一种线程安全的方式操作非线程安全对象的某些字段
 * 需求：
 * 10个线程，
 * 每个线程转账1000，
 * 不使用Synchronized，尝试使用AtomicIntegerFieldUpdater
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        //AtomicIntegerFieldUpdater<BankAccount> accountAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                       // bankAccount.add();
                        //accountAtomicIntegerFieldUpdater.getAndIncrement(bankAccount);
                        bankAccount.transMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }

            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t" + bankAccount.money);

    }
}
