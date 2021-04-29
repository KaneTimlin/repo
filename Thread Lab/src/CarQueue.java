import java.util.ArrayDeque;
import java.util.Random;

public class CarQueue {

    ArrayDeque<Integer> queue;
    Random rand;

    public CarQueue() {

        queue = new ArrayDeque<>();
        rand = new Random();

        for (int i = 0; i < 5; i++) {
            queue.add(rand.nextInt(4));
        }
    }

    public void addToQueue() {
        class AnimationThread implements Runnable {

            @Override
            public void run() {
                while (true) {
                    try {
                        queue.add(rand.nextInt(4));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Ending CarQueue Thread");
                        break;
                    }
                }
            }
        }
        AnimationThread animationThread = new AnimationThread();
        Thread t1 = new Thread(animationThread);
        t1.start();
    }

    public int deleteQueue() {
        return queue.remove();
    }

}
