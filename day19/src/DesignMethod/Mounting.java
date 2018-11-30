package DesignMethod;

class Mounting implements Work {

    Work w;//内部一个被装饰的类

    public Mounting(Work w) {

        this.w = w;
    }

    @Override
    public void work() {

        w.work();
        System.out.println("给画装裱");
    }
}
