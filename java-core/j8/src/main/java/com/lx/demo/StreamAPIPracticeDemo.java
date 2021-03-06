package com.lx.demo;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPIPracticeDemo {
    public static void main(String[] args) {

        //{4=[lisi, maqi], 6=[wangwu, yangba], 7=[zhaoliu], 8=[zhangsan]}
        countWordTimes(Stream.of("zhangsan", "lisi", "wangwu", "zhaoliu", "maqi", "yangba"));

        final double doubleAverage = doubleAverage(Stream.of(1L, 2L, 3L));
        assert doubleAverage == 2.0;

        final ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        final ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(4, 5, 6));
        final ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(7, 8, 9));
        final Stream<List<Integer>> listStream = Stream.of(list1, list2, list3);
        final Stream<Integer> integerStream = streamListObjToStreamObject(listStream);
        assert integerStream.count() == 9;

        final boolean finite = isFinite(Stream.generate(() -> 0));
        assert finite;

        characterStream();

        intStream();

//        Stream.generate(()->"echo").limit(20).forEach(System.out::println);
        processCount();

        //找前五个最长单词
        findMaxSizeStrLimit5();
    }

    /**
     * 每个长度对应单词个数
     */
    private static void countWordTimes(Stream<String> stream) {
        // 根据字符串长度分组
        final Map<Integer, List<String>> collect = stream.collect(Collectors.groupingBy(String::length));
        System.out.println(collect);
    }

    private static double doubleAverage(Stream<Long> stream) {
        //计算平均值
        final DoubleSummaryStatistics doubleSummaryStatistics =
                stream.mapToDouble(x -> x).summaryStatistics();
        System.out.printf("平均值为: %s\n", doubleSummaryStatistics.getAverage());
        return doubleSummaryStatistics.getAverage();
    }

    /**
     * Stream<List> To Stream<Obj>
     */
    private static <T> Stream<T> streamListObjToStreamObject(Stream<List<T>> stream) {
        //方法1, 先把这玩意儿转成多个stream然后合并stream参考　flatmap聚合操作
        // 这个好用
        final Stream<T> tStream = stream.map(l -> l.stream()).flatMap(stream1 -> stream1);
        return tStream;

        //方法2 这种方式小心 Exception in thread "main" java.lang.UnsupportedOperationException
        // 不支持arrays.aslist这类unmidify类型的合并
//        final Stream<T> stream1 = stream.reduce((ts, c) -> {
//            ts.addAll(c);
//            return ts;
//        }).get().stream();
//        stream1.forEach(System.out::println);
//        return null;
    }

    /**
     * 判断无限流
     * @param stream
     * @param <T>
     * @return
     */
    public static <T> boolean isFinite(Stream<T> stream){
//        stream.count();
        //todo
        return false;
    }

    /**
     * 字符串转character stream
     */
    private static void characterStream() {
        String s = "hello";
        final Stream<Character> characterStream =
                Stream.iterate(BigInteger.ZERO, a -> a.add(BigInteger.ONE)).limit(s.length())
                        .map(integer -> s.charAt(Integer.parseInt(integer.toString())));
        characterStream.forEach(System.out::println);
    }

    private static void intStream() {
        int[] values = {1, 3, 4, 6, 8};
        // 数组类型stream
        final Stream<int[]> values1 = Stream.of(values);
        // 返回数组内部类型的stream
        final IntStream stream = Arrays.stream(values);
    }

    private static void findMaxSizeStrLimit5() {
        final Random random = new Random();
        final List<String> list = Stream.generate(() -> getRandomString2(random.nextInt(10))).limit(80).collect(Collectors.toList());
        //80选五个
        list.stream()
                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .limit(5)
                .forEach(System.out::println);
        //
//        list.stream().filter(s -> {
//            System.out.println("调用xx");
//            return s.length() > 3;
//        }).limit(5).forEach(System.out::println);

    }

    /**
     * 处理处理器个数的线程
     * 多个线程统计总数比较是否相同
     */
    public static void processCount() {
        final int processors = Runtime.getRuntime().availableProcessors();
        // 创建80个任意最大长度为10的字符串
        final Random random = new Random();
        final List<String> list = Stream.generate(() -> getRandomString2(random.nextInt(10))).limit(80).collect(Collectors.toList());
        int index = 0;
        ExecutorService ex = Executors.newFixedThreadPool(processors);
        int perDealSize = 10;
        List<Future<Integer>> futures = new ArrayList<>(processors);

        //分配
        for (int i = 0; i < processors; i++, index += perDealSize) {
            if (index >= list.size()) break;
            int end = index + perDealSize;
            end = Math.min(end, list.size());

            futures.add(ex.submit(new Task(list, index, end)));
        }
        try {
            int count = 0;
            for (Future<Integer> future : futures) {
                //合并操作
                count += future.get();
            }

            // 测试自己写的统计跟j8的方式是否一致
            assert count == list.parallelStream().mapToInt(String::length).sum();
            System.out.printf("字符总数 %s \n", count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成指定length的随机字符串（A-Z，a-z，0-9）
    public static String getRandomString2(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }
}

class Task implements Callable<Integer> {

    private List<String> list;
    private int start;
    private int end;

    public Task(List<String> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        String obj = null;
        for (int i = start; i < end; i++) {
            obj = list.get(i);
            //你的处理逻辑
            count += obj.length();
        }
        //返回处理结果
        return count;
    }

}
