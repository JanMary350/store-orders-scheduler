import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TaskScheduler {
    public static String[][] scheduler(String[][] orderData, String[] storeData, String[] workers) {
        String result[][] = new String[orderData.length][3];
//        System.out.println("scheduler");
        int howMuchTime; //how much time the store is working
        int workerCollectsFor; //how much time the worker already assigned to tasks
        int ordersIterator; //iterator for orders to make every worker iterate through every available order
        int pickingDuration; //how much time it takes to pick an order
        int timeFromBeggining; //how much time is left from the beggining of picking to the end of task
        LocalTime pickingStartTime = LocalTime.parse(storeData[0]);
        LocalTime pickingEndTime = LocalTime.parse(storeData[1]);
        howMuchTime = (int) ChronoUnit.MINUTES.between(pickingStartTime, pickingEndTime);

        for (int i = 0; i < workers.length; i++) {
            workerCollectsFor = 0;
            ordersIterator = 0;
            while(workerCollectsFor < howMuchTime && ordersIterator < orderData.length) {
                pickingDuration = (int) Duration.parse(orderData[ordersIterator][1]).toMinutes(); //pickingTime
                LocalTime endOfTask = LocalTime.parse(orderData[ordersIterator][3]); //completeBy
                timeFromBeggining = (int) ChronoUnit.MINUTES.between(pickingStartTime, endOfTask); //time from start of picking to the end of task
                if (workerCollectsFor+pickingDuration <= timeFromBeggining && result[ordersIterator][0] == null) {
                    //if worker can collect order and order is not already assigned
                    result[ordersIterator][0] = workers[i]; //now order is assigned to worker
                    result[ordersIterator][1] = orderData[ordersIterator][0]; //orderId
                    result[ordersIterator][2] =(pickingStartTime.plusMinutes(workerCollectsFor)).toString(); //time when worker starts picking
                    workerCollectsFor += pickingDuration; //worker is assigned for a longer time
                }
                ordersIterator++;
            }
        }
        return result;
    }
}
