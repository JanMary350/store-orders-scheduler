import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.lang.management.ThreadInfo;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class OrdersProcessor {
    HashMap ordersMap;
    String[][] orderData;
    String path;
    public OrdersProcessor(String path) {
        this.path = path;
        separateOrders(this.path);
    }
    public void optimize(boolean byValue) {
        if (byValue) {
            optimizeDataByValue();
        } else {
            optimizedDataByTime();
        }
    }
    public void processOrders() {

        orderDataSeparator(this.ordersMap);
    }
    public void changePath(String path) {
        this.path = path;
        separateOrders(this.path);
    }
    public String[][] getOrderData() {
        return this.orderData;
    }

    public HashMap getOrdersMap() {
        return this.ordersMap;
    }

    private void separateOrders(String jsonString) {
        //Read JSON file
        JSONParser parser = new JSONParser();
        HashMap<Integer, JSONObject> ordersMap = new HashMap<>();
        int iterator = 0; //iterator for HashMap
        try {
            JSONArray orders = (JSONArray) parser.parse(new FileReader(jsonString));
            for (Object order : orders) {
                JSONObject jsonOrder = (JSONObject) order;
                ordersMap.put(iterator, jsonOrder);
                iterator++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ordersMap = ordersMap;
    }

    private void orderDataSeparator(HashMap ordersMap) {
        //creates string table for easier data handling
        JSONObject order = (JSONObject) ordersMap.get(0);
        String[][] orderData = new String[ordersMap.size()][4];
        for (int i = 0; i < ordersMap.size(); i++) {
            order = (JSONObject) ordersMap.get(i);
            orderData[i][0] = order.get("orderId").toString();
            orderData[i][1] = order.get("pickingTime").toString();
            orderData[i][2] = order.get("orderValue").toString();
            orderData[i][3] = order.get("completeBy").toString();
        }
        this.orderData = orderData;
    }
    private void optimizedDataByTime() {
        Arrays.sort(this.orderData, Comparator.comparing(row -> row[1]));
    }

    private void optimizeDataByValue() {
        String[][] orderDataWithValueToTimeRatio = new String[this.orderData.length][5];
        //rewrite string to add extra column with value/time ratio
        for (int i = 0; i < this.orderData.length; i++) {
            orderDataWithValueToTimeRatio[i][0] = this.orderData[i][0];
            orderDataWithValueToTimeRatio[i][1] = this.orderData[i][1];
            orderDataWithValueToTimeRatio[i][2] = this.orderData[i][2];
            orderDataWithValueToTimeRatio[i][3] = this.orderData[i][3];
            float duration = (float) Duration.parse(this.orderData[i][1]).toMinutes();
            float value = Float.parseFloat(this.orderData[i][2]);
//            System.out.println("duration: " + duration + " value: " + value);
//            System.out.println(String.valueOf(duration / value));
            orderDataWithValueToTimeRatio[i][4] = String.valueOf(duration / value);
        }
        Arrays.sort(orderDataWithValueToTimeRatio, Comparator.comparing(row -> row[4]));
        this.orderData = orderDataWithValueToTimeRatio;
    }
}
