import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class StoreProcessor {
    JSONObject store;
    String path;
    String[] storeData;
    String[] workers;
    public StoreProcessor(String path) {
        this.path = path;
        separateStore(this.path);
    }
    public void changePath(String path) {
        this.path = path;
        separateStore(this.path);
    }
    public String[] getStoreData() {
        return this.storeData;
    }
    public String[] getWorkers() {
        return this.storeData;
    }
    public void processStore() {
        this.storeData = separateStoreData(this.store);
        this.workers = separatePickers(this.store);
    }

    public static String[] separateStoreData(JSONObject store) {
        //creates string with open-close hours
        String[] storeData = new String[2];
        storeData[0] = store.get("pickingStartTime").toString();
        storeData[1] = store.get("pickingEndTime").toString();
        return storeData;
    }

    private void separateStore(String jsonString) {
        //Read JSON file
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try (FileReader reader = new FileReader(jsonString)) {
            //Read JSON file
            Object obj = parser.parse(reader);
            jsonObject = (JSONObject) obj;
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
        this.store = jsonObject;
    }
    public static String[] separatePickers(JSONObject store) {
        //separates pickers from JSONArray
        JSONArray pickers = (JSONArray) store.get("pickers");
        String[] workers = new String[pickers.size()];
        for (int i = 0; i < pickers.size(); i++) {
            workers[i] = pickers.get(i).toString();
        }
        return workers;
    }
}
