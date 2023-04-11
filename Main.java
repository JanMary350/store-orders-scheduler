import org.json.simple.JSONObject;

import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter path to store.json file:");
        String pathToStore = scanner.nextLine();
        System.out.println("Enter path to orders.json file:");
        String pathToOrders = scanner.nextLine();
        OrdersProcessor ordersProcessor = new OrdersProcessor(pathToOrders);
        ordersProcessor.processOrders();
        ordersProcessor.optimize(true);
        //if you want to optimize by time, change true to false
        StoreProcessor storeProcessor = new StoreProcessor(pathToStore);
        storeProcessor.processStore();
        String[] storeData = storeProcessor.getStoreData();
        String[] workers = storeProcessor.getWorkers();
        String[][] ordersData = ordersProcessor.getOrderData();
        ResultPrinter.printResult(TaskScheduler.scheduler( ordersData, storeData, workers)); //print result
    }
}