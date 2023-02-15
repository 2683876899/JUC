package locks;


/**
 * javap -c ****.class 文件反编译
 * javap -v ****.class 输出附加信息（包括行号、本地变量表、反编译等详细信息）
 *
 * 同步方法块： monitorenter，monitorexit
 * 同步方法：flags：ACC_SYNCHRONIZED
 * 同步静态方法：flags：ACC_STATIC,ACC_SYNCHRONIZED
 *
 * 管程（英语：Monitors，也称为监视器）是一种程序结构，结构内的多个子程序（对象或模块）形成的名个工作线程互斥访问共享资源。
 * 这些共享资源一般是硬件设备或一群变量。对共享变量能够进行的所有操作集中在一个模块中。（把信号量及其操作原语“封装”在一个对象内部）
 * 管程实现了在一个时间点，最多只有一个线程在执行管程的某个子程序。管程提供了一种机制，管程可以看做一个软件模块，它是将共享的变量和对
 * 手这此共享变量的操作封装起来，形成一个具有一定接口的功能模块，进程可以调用管程来实现进程级别的并发控制.
 */
public class LockSyncDemo {
    private static Object o = new Object();

    public void  m1(){
        synchronized (o){
            System.out.println("in sync code block!");
        }
    }

    public synchronized void m2(){
        System.out.println("hello m2");
    }

    public static synchronized void m3(){
        System.out.println("hello m2");
    }

    public static void main(String[] args) {

    }
}
