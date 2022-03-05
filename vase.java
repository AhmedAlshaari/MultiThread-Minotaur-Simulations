import java.util.*;
import java.util.concurrent.*;

class vase
{ 
	static BlockingQueue<Integer> queue;
  static int n;
	static int visits;
	
  public static class regThread extends Thread
  {
    int num;
		boolean leadFlag;
		
    public regThread(int num, boolean leadFlag) 
    {
      this.num = num;
			this.leadFlag = leadFlag;
    }

    @Override
    public void run()
    {
			if (this.leadFlag)
      {
				int count = 0;
				while (count < visits)
				{
					if (!queue.isEmpty())
					{
						count++;
						System.out.println("Visit #" + count + ": visitor " + queue.poll() + " currently visiting");
					}
				}

				System.out.println("The Crystal Vase has been visited " + visits + " times, simulation successful!");
			}
			else
			{
				int c = 0;
				while (c < visits)
        {
          try
          {
            c++;
						queue.add(this.num);
          }
          finally
          {
						try
						{
							Thread.sleep(5);
						}
						catch (InterruptedException e)
						{
							
						}
          }
        }
			}
    }
  }

  public static void main(String[] args)
  {
    Scanner stdin = new Scanner(System.in);

		queue = new LinkedBlockingDeque<Integer>();
		System.out.println("This is a simulation of the second problem of HW2, Minotaur’s Crystal Vase:");
		System.out.println("How many guests would you like to invite to the have a chance at seeing the vase?");
    n = stdin.nextInt();
		System.out.println("How many visits to the showroom would you like to allow?");
		visits = stdin.nextInt();

    regThread[] threads = new regThread[n + 1];

    for (int i = 0; i <= n; i++)
    {
      if (i == 0)
        threads[i] = new regThread(i, true);
      else
        threads[i] = new regThread(i, false);
      threads[i].start();
    }
  }
}