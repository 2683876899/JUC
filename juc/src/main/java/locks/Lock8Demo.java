package locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Phone{

    public synchronized void sendEMail(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("sendEMail");
    }

    public synchronized void sendSMS(){
        System.out.println("sendSMS");
    }

    public void hello(){
        System.out.println("hello");
    }
}


/**
 * 题目：谈谈你对多线程锁的理解，8种案例说明
 * 口诀：线程，操作，资源类
 * 8锁案例说明：
 * 1. 标准访问ab两个线程，请问先打印邮件还是短信
 * 2. sendEMail方法中加入暂停3秒钟，请问先打印邮件还是短信
 * 3. 添加一个普通的hello方法，请问先打印邮件还是hello
 * 4. 有两部手机，请问先打印邮件还是短信
 * 5. 有两个静态同步方法，有1部手机，请问先打印邮件还是短信
 * 6. 有两个静态同步方法，有2部手机，请问先打印邮件还是短信
 * 7. 有一个静态同步方法，有1部手机，请问先打印邮件还是短信
 * 8. 有一个静态同步方法，有2部手机，请问先打印邮件还是短信
 *
 * 笔记总结：
 * 1-2
 * 一个对象里面如果有多个Synchronized方法，某一个时刻内，只要一个线程去调用其中的一个Synchronized方法了，其他
 * 线程只能等待。换句话说，某一时刻内，只能有唯一的一个线程去访问Synchronized方法，锁的是当前对象this，被锁定后
 * 其他线程都不能进入到当前对象的其他Synchronized方法
 * 3-4
 * 加个普通方法发现和同步锁无关
 * 换成两个对象后，不是同一把锁了，情况立刻变化。
 * 5-6
 * 都换成静态同步方法后，锁的内容又有一些差别：
 * 对于普通同步方法，锁的是当前实例对象，通常是this，具体的一部部手机，所有的普通同步方法都是用的同一把锁->实例对象本身
 * 对于静态同步方法，锁的是当前类的Class对象，如Phone.class唯一的一个模板
 * 对于同步方法块，锁的是Synchronized括号内的对象
 * 7-8
 * 当一个线程试图访问同步代码时它首先必须获得锁，正常推出或抛出异常时必须释放锁。
 * 所有的普通同步方法用的都是同一把锁———实例对象本身，就是new出来的具体实例对象本身，本类this
 * 也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须要等获取锁的方法释放锁以后才能获得锁
 * 所有的静态同步方法用的也是同一把锁———类对象本身，也就是我们说过的唯一模板Class
 * 具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞争条件的
 * 但是一旦一个静态同步方法获取锁后，其他静态同步方法必须都等待该方法释放锁以后才能获取锁
 *
 * 作用于实例方法，当前实例加锁，进入同步代码前要获得当前实例的锁
 * 作用于代码块，对括号里配置的对象加锁
 * 作用于静态方法，当前类加锁，进去同步代码前要获得当前类对象的锁
 */
public class Lock8Demo {

    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();


        new Thread(phone::sendEMail,"t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(phone::sendSMS,"t1").start();
    }
}
