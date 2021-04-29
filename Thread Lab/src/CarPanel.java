import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
   This component draws two car shapes.
*/
public class CarPanel extends JComponent
{  
	private Car car1;
	private int x,y, delay;
	private CarQueue carQueue;
	private int direction;

	public final int UP = 0;
	public final int DOWN = 1;
	public final int RIGHT = 2;
	public final int LEFT = 3;
	
	CarPanel(int x1, int y1, int d, CarQueue queue)
	{
		delay = d;
        x=x1;
        y=y1;
        car1 = new Car(x, y, this);
        carQueue = queue;
	}
	public void startAnimation() {
	       class AnimationRunnable implements Runnable {
	          public void run() {
	             try {
	            	for(int i=0;i<10;i++)
	            	{
	            	   direction = carQueue.deleteQueue();
						if (direction == UP) {
							y = (y - 10 <= 0 ? y + 10 : y - 10);
						} else if (direction == DOWN) {
							y = (y + 10 >= 400 ? y - 10 : y + 10);
						} else if (direction == RIGHT) {
							x = (x + 10 >= 300 ? x - 10 : x + 10);
						} else if (direction == LEFT) {
							x = (x - 10 <= 0 ? x + 10 : x - 10);
						}
						repaint();
	            	   Thread.sleep(delay*1000);
	            	   
	               }
	            }
	            catch (InterruptedException exception)
	            {
	            	System.out.println("Stopping animation");
	            }
	            finally
	            {
	            	
	            }
	         }
	      }
	      
	      Runnable r = new AnimationRunnable();
	      Thread t = new Thread(r);
	      t.start();
	   }
	
   public void paintComponent(Graphics g)
   {  
      Graphics2D g2 = (Graphics2D) g;

      car1.draw(g2,x,y);    
   }
}