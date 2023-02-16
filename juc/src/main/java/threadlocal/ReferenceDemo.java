package threadlocal;

class MyObject{


    //这个方法一般不用重写，只是为了演示案例做说明
    @Override
    protected void finalize() throws Throwable {
        // 通常目的是在对象被不可撤销地丢弃之前执行清理操作
        System.out.println("---- invoke finalize");
    }
}
public class ReferenceDemo {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        System.out.println("gc before:"+myObject);

        myObject = null;
        System.gc();
        System.out.println("gc after"+myObject);
    }
}
