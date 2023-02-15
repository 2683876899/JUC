package locks;


/**
 * 1. 中断标志位，默认是false
 * 2. t2 -----> t1发送了中断协商，t2跳用t1.interrupt()，中断标志位：true
 * 3. 中断标志位true，正常情况，程序停止
 * 4. 中断标志为true，异常情况：InterruptedException，将会把中断状态清除，并且收到InterruptedException。
 *      中断标志位为false，导致无限循环。
 * 5. 在catch块中，需要再次将中断标志位设为true，2次调用停止程序才OK
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("调用interrupt()方法，此时中断标志位为："+Thread.currentThread().isInterrupted());
                    break;
                }
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("------- hello interruptDemo3");
            }
        }, "t1");

        t1.start();
        System.out.println("此时中断标志位为："+Thread.currentThread().isInterrupted());
        new Thread(t1::interrupt,"t2").start();

    }
}
