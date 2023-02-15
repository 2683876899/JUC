package future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 需求说明：电商比价秀求，模拟如下情况：
 * 1 需求：
 * 	1.1同一款产品，同时搜索出向款产品在各大电商平合的售价；
 * 	1.2同一款产品，同时搜索出大产品在同一个电商平台下，入驻卖家售价是多少
 * 2输出：出来结果希望是同款产品的在不同地方所价格清单列表，返回一个List<String>
 * 	(mysql) in jd price is 88.05
 * 	(mysql)in dangdang price is 86.11
 * 	(mysql) in taobao price is 90.43
 * 3 技术要求
 * 	3.1 函数式编程
 * 	3.2 链式编程
 * 	3.3 Stream流式计算
 */
public class CompletableFutureMallDemo {

    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    /**
     * step by step 一家一家搜索
     * List<NetMall> ----> map ----> List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list,String productName){
        //(mysql) in jd price is 88.05
        return list.stream().map(netMall -> String.format(productName + "in %s price is %.2f", netMall.getNetMallName(), netMall.calcPrice(productName))).collect(Collectors.toList());
    }

    /**
     * List<NetMall> ----> ???? ----> List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list,String productName){
        //(mysql) in jd price is 88.05
        return list
                .stream()
                .map(netMall -> CompletableFuture.supplyAsync(() ->
                    String.format(productName + "in %s price is %.2f", netMall.getNetMallName(), netMall.calcPrice(productName))
                )).collect(Collectors.toList()).stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //System.out.println(getPrice(list, "mysql"));
        System.out.println(getPriceByCompletableFuture(list, "mysql"));
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行完成耗时："+ (endTime - startTime));
        System.out.println("--------------");
        startTime = System.currentTimeMillis();
        System.out.println(getPrice(list, "mysql"));
        endTime = System.currentTimeMillis();
        System.out.println("程序运行完成耗时："+ (endTime - startTime));
    }

    /**
     * 链式调用
     */
    private static void chainTest() {
        // chain链式调用
        Student student = new Student();
        student.setId(1).setStudentName("ss").setMajor("ss");
        System.out.println(student);
    }
}

class NetMall
{
    @Getter
    private String netMallName;

    public NetMall(String netMallName)
    {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName){
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}


@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
class Student{
    private Integer id;
    private String studentName;
    private String major;
}
