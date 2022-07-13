package over.achievers.database.SQLServer;

import over.achievers.database.model.Logger;

import java.util.Arrays;
import java.util.Collection;

public class ThreadPool{
    public static <E> void forEach(final Collection<E> coll, ThreadPoolTask<E> task, final int threadCount){
        int internalThreadCount=threadCount;
        if(internalThreadCount<=0)
        {
            internalThreadCount = Runtime.getRuntime().availableProcessors();
        }

        Thread[] threads = new Thread[threadCount];

        final Object[] array = coll.toArray(new Object[0]);
        for(int i=0;i< Arrays.stream(threads).count();i++)
        {
            final int arrayBegin=coll.size()/internalThreadCount*i;
            final int arrayEnd=coll.size()/internalThreadCount*(i+1);
            threads[i]=new Thread(()->{
                for(int j=arrayBegin;j<arrayEnd;j++)
                {
                    task.run((E)array[j]);
                }
            });
            threads[i].start();
        }
        for(Thread th : threads)
        {
            try {
                th.join();
            }
            catch(InterruptedException e)
            {
                //TODO add some logging
                Logger.debug(String.valueOf(e.getStackTrace()));
            }
        }

    }
}
/*????
public class MyRunnable implements Runnable {
      private X parameter;
      public MyRunnable(X parameter) {
         this.parameter = parameter;
      }

      public void run() {
      }
   }
   Thread t = new Thread(new MyRunnable(parameter));
   t.start();
 */