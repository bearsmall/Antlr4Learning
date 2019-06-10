package clone.demo1;

public class B {
    public double test(int x1,int x2,int y1,int y2){
        int x = x2-x1;
        int y = y2-y2;
        new Thread(() -> System.out.println("Thread run()")).start();
        return Math.sqrt(x*x+y*y);
    }
}
