package object;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * JOL:Java Object Layout
 * 1.查看虚拟机运行时开启的配置 java -XX:+PrintCommandLineFlags -version
 * 2.默认开启了压缩指针-XX:+UseCompressedClassPointers
 * 3.手动关闭压缩-XX:-UseCompressedClassPointers
 */
public class JOLDemo {

    public static void main(String[] args) {
       /* // Thread.currentThread
        //System.out.println(VM.current().details());
        System.out.println(VM.current().objectAlignment());*/
        Object o = new Object(); // 16 bytes
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        // Customer customer = new Customer(); // 1.16 bytes,2.24 bytes
        // System.out.println(ClassLayout.parseInstance(customer).toPrintable());
    }
}

class Customer{
    // 1.第一种情况，只有对象头，没有其他任何实例数据
    // 2.第二种情况，int + boolean 默认满足对齐填充 24bytes
    int id;
    boolean flag = false;
    //String name = "风景";
}
