package DesignMethod;

//装饰者设计模式：如果想对已经存在的对象进行装饰，那么就定义一个类，
// 在类中对已经有的对象进行功能的增强或添加另外的行为，这个类就叫装饰者类。
// 被修饰的类叫被装饰者类，是已经存在有的功能。在装饰者类之间又可以互相装饰
//特点：
//  1.装饰类通过构造方法来接收被装饰者的对象，调用它里面的功能或行为
//  2. 基于对被装饰对象的功能进行扩展，提供更强大的功能

import java.io.FileNotFoundException;

public class Decorator {
    public static void main(String[] args) throws FileNotFoundException {

        Work w = new Drawing();
        Colour c = new Colour(w);
        Mounting m = new Mounting(c);
        m.work();
    }

}
