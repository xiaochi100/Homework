package stream_api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestHero {

    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("heroes.txt"), Charset.forName("utf-8"));
        //lines.forEach(str-> System.out.println(str));

        //需要重写
        Stream<Hero> heroStream = lines.map(str -> str.split("\t")).map(array ->
                new Hero(
                        Integer.parseInt(array[0]),
                        array[1], array[2], array[3],
                        Integer.parseInt(array[4]),
                        Integer.parseInt(array[5]),
                        Integer.parseInt(array[6]))
        );

        //输出武力值最高的三个英雄
        //heroStream.sorted((a,b)->(b.getPower()-a.getPower())).limit(3).forEach(a-> System.out.println(a.getName()+"\t"+a.getPower()));
        //结果是：


        //输出寿命前三的武将
//        heroStream.sorted((a,b)->((b.getDeath()-b.getBirth())-(a.getDeath()-a.getBirth()))).limit(3).forEach(a-> System.out.println(a.getName()+"\t"+(a.getDeath()-a.getBirth())));
//结果是：
//        吕岱	95
//        廖化	94
//        司马孚	92

        //输出寿命最高的女人
        // heroStream.filter(a->a.getSex().equals("女")).sorted((a,b)->((b.getDeath()-b.getBirth())-(a.getDeath()-a.getBirth()))).limit(3).forEach(a-> System.out.println(a.getName()+"\t"+(a.getDeath()-a.getBirth())));

        //结果是
        //辛宪英	78
        //曹节	70
        //卞氏	70

        //按出生地分组
        //Map<String, List<Hero>> collect = heroStream.collect(Collectors.groupingBy((a) -> a.getLoc()));

        //collect.forEach((a,b)-> System.out.println(a+":"+b.stream().map(c->c.getName()).collect(Collectors.toList())));

        //按出生地分组后，确定每个组的人数

        // Map<String, Long> collect1 = heroStream.collect(Collectors.groupingBy((c)->c.getLoc(),Collectors.counting()));


        //collect1.forEach((a,b)-> System.out.println(a+":"+b));

        //按各武力值段分组 >=80;70~79;60~69;<60;共分为四组

//        Map<String, List<Hero>> collect2 = heroStream.collect(Collectors.groupingBy(a -> PowerArrange(a.getPower())));


//        collect2.forEach((a,b)-> System.out.println(a+":"+b.stream().map(c->c.getName()).collect(Collectors.toList())));

        //按年龄分组   <40;40~49;50~59;60~69;>70;
        Map<String, List<Hero>> collect3 = heroStream.collect(Collectors.groupingBy(a -> AgeArrange(a.getDeath() - a.getBirth())));

        collect3.forEach((a,b)-> System.out.println(a+":"+b.stream().map(c->c.getName()).collect(Collectors.toList())));

    }

    public static  String PowerArrange(int power){
        if(power>=80){
            return "80以上";

        }
        if(power>=70&&power<=79){
            return "70~79";

        }
        if(power>=60&&power<=69){
            return "60~69";
        }
        if(power<60){
            return "小于60";
        }
        return  "出错";
    }

    public  static  String AgeArrange(int age){
        if(age<40){
            return "小于40";

        }
        if(age>=40&&age<=49){
            return "40~49";
        }
        if(age>=50&&age<=59){
            return  "50~59";

        }
        if(age>=60&&age<=69){
            return  "60~69";
        }
        if(age>=70){
            return  "大于等于70";
        }
        return "出错";
    }

}