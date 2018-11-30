package DesignMethod;

public class Colour implements  Work {

Work w;//内部一个被修饰的类
    public  Colour(Work w){
        this.w=w;
    }
    @Override
    public void work() {
    w.work();
        System.out.println("给画上色");

    }
}
