import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class DataThread extends Thread {

    private volatile ArrayList<Data> dataList;
    private volatile Semaphore s;
    private String name;
    private ObjectMapper mapper = new ObjectMapper();
    private final String KEY = "ea671e759f49490ba9f81f168a29dc5d";

    private final String URL;
    // Top head lines
    // Everything
    public DataThread(ArrayList<Data> dataList, Semaphore s, String url) {
        this.dataList = dataList;
        this.s = s;
        this.URL = url ;
    }


    @Override
    public void run() {
        try {
            java.net.URL fetch = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) fetch.openConnection();
            conn.setRequestProperty("Authorization", KEY);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("An error occured");
            }

            // deserialize the json
            JsonNode node = mapper.readTree(conn.getInputStream()).get("articles");
            if (node.isArray()) {
                for (JsonNode n : node) {
                    String str = mapper.writeValueAsString(n);
                    Data data = mapper.readValue(str, Data.class);

                    // acquire the lock and write to the shared resource
                    try {
                        s.acquire();
                        this.dataList.add(data);
                        s.release();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}