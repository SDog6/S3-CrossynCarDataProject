package Backend.Accepter;

import Backend.Classes.TripEntry;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class AccepterFlow
{
    BlockingQueue<TripEntry> queue;

    public AccepterFlow(BlockingQueue<TripEntry> queue)
    {
        this.queue = queue;
    }

    public void init() throws IOException {
        while(true)
        {
            //System.out.println("Debug purpose; You want to use dialog popup to select dataset? (write true or false)"); //popup doens't work anymore
            //Scanner input = new Scanner(System.in);
            //boolean set = input.nextBoolean();



            TripEntryAccepter TE = new TripEntryAccepter();

            //String finalLine = TE.BigLine();

            List<TripEntry> list = TE.TurnJSONStringToObject(TE.BigLine());
//            if (set) {
//                list = TE.TurnJSONStringToObject(TE.BigLineDialog());
//            } else {
//                list = TE.TurnJSONStringToObject(TE.BigLine());
//            }


            for (TripEntry Test : list) {
                try {
                    //queue.add(Test);
                    if (queue.isEmpty()) {
                        synchronized (queue) {
                            queue.notify(); // notify and wake the algorithm

                        }
                    }

                    queue.put(Test);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Done Reading and posting to queue, ready for new dataset.");

        }
    }
}
