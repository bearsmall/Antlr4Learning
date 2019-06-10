package clone.demo1;

public class A {
    public double test(int a1,int a2,int b1,int b2){
        int x = a2-a1;
        int y = b2-b2;
        new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("hello world");
            }
        }).start();
        return Math.sqrt(x*x+y*y);
    }
}
