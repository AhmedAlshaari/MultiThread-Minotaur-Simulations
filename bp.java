import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class bp
{ 
  static boolean cupCake;
  static ReentrantLock lock = new ReentrantLock();
  static int n;
	static boolean flag;
  
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
        int count = 1;

        while (count < n)
        {
          lock.lock();
          try
          {
            if (!cupCake)
            {
              count++;
              cupCake = true;
            }
          }
          finally
          {
            lock.unlock();
          }
        }
				flag = false;
        System.out.println("All " + count + " visitors have entered the labyrinth, simulation successful!");
      }
      else
      {
        while (flag)
				{
          lock.lock();
          try 
          {
            if (cupCake)
            {
              cupCake = false;
            }
          }
          finally
          {
            lock.unlock();
          }
        }
      }
    }
  }

  public static void main(String[] args)
  {
    Scanner stdin = new Scanner(System.in);
    cupCake = true;
		flag = true;
		System.out.println("This is a simulation of the first problem of HW2, Minotaur’s Birthday Party:");
		System.out.println("How many quests would you like to invite to the Minotaur's birthday party?");
    n = stdin.nextInt();

    regThread[] threads = new regThread[n];

    for (int i = 0; i < n; i++)
    {
      if (i == 0)
        threads[i] = new regThread(i + 1, true);
      else
        threads[i] = new regThread(i + 1, false);
      threads[i].start();
    }
  }
}