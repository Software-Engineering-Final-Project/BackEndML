import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ThreadPooling {

    public static void main(String[] args) {
        ArrayList<Data> dataList1 = new ArrayList();
        ArrayList<Data> dataList2 = new ArrayList();
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);

        int numOfCores = Runtime.getRuntime().availableProcessors();

        // Create a thread pool, so we can reuse threads
        ExecutorService pool = Executors.newFixedThreadPool(numOfCores + 1);

        String[] urls = new String[] {
                "https://newsapi.org/v2/everything?q=autism&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?qInTitle=autism&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?q=developmental-disabilities&sortBy=popularity&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?q=autism-speaks&sortBy=popularity&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?q=autism-spectrum-disorder&sortBy=popularity&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?q=autism-treatment&sortBy=popularity&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?qInTitle=aspergers&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?q=individualized-education-program&sortBy=popularity&pageSize=100&language=en",
                "https://newsapi.org/v2/everything?q=individualized-education-program&sortBy=popularity&pageSize=100&language=en",
        };

        int iterations = 0;
        while (iterations < 8) {
            pool.execute(new DataThread(dataList1, s1, urls[iterations]));
            pool.execute(new DataThread(dataList2, s2, urls[iterations + 1]));
            iterations+=2;
        }

        pool.shutdown();

        try {
            pool.awaitTermination(10, TimeUnit.DAYS);
            dataList1.addAll(dataList2);
            ArrayList<Data> cleaned_sql_list = new ArrayList<>();
            // Clean Data
            cleaned_sql_list = DataCleaner.cleanData2(dataList1);



            HashSet<Data> sqlSet = new HashSet<>();

            sqlSet.addAll(cleaned_sql_list);

            // Write to csv
            PrintWriter writer1 = new PrintWriter("src/main/java/allData.txt");
            PrintWriter writer2 = new PrintWriter("src/main/java/selectedData.txt");
            PrintWriter sqlWriter = new PrintWriter("src/main/java/articles.sql");
            writeToSQL(sqlWriter, sqlSet);
            sqlWriter.close();
            ArrayList<Data> cleaned_list = DataCleaner.cleanData(dataList1);
            HashSet<Data> dataSet = new HashSet<>();
            dataSet.addAll(cleaned_list);
            writeToCSVS(writer1, dataSet, writer2);

            System.out.println("Finished writing to CSV file");
            writer1.close();
            writer2.close();



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Max time exceeded");
        }
    }

    private static void writeToSQL(PrintWriter writer, HashSet<Data> dataSet){
        int data_id = 1;
        writer.println("INSERT into article (id_article, title, desc, author, fk_category_id) values ");
            for (Data data : dataSet){
                writer.println("(" +  data_id + ", " +  "\"" + data.getTitle() + "\"" + ", " +  "\"" + data.getDescription() + "\"" + ", " +
                                "\"" + data.getAuthor() + "\"" + ", " +  getSourceID(data.getSourceName())  + ")" +
                        (data_id == dataSet.size() ? ";" : ","));
                data_id += 1;
            }

    }
    private static int getSourceID(String source){
        switch (source) {
            case "IEEE": return 3;
            case "Forbes": return 4;
            case "NIH": return 5;
            case "Google News": return 6;
            case "The New Yorker": return 7;
            case "WebMD" : return 8;
            case "ABC News": return 9;
            case "USA Today": return 10;
            case "Psychology Today":return 11;
            case "CNN": return 1;
            case "TED" : return 12;
            case "Science": return 13;
            case "Scientific American": return 14;
            default: return 2;
        }
        }
    private static void writeToCSVS(PrintWriter writer, HashSet<Data> dataSet, PrintWriter writer2) {
        int line_num = 1;
        for(Data data : dataSet) {
            writer.println(line_num + ", " + data.getTitle() + ", " + data.getAuthor() + ", " + data.getDescription()
                    + ", " + data.getPublishedAt() + ", " + data.getUrl());

            //writer2.println(line_num + ", " + data.getSourceName());

            writer2.println(line_num + ", \"" + data.getTitle() + " - " + data.getDescription() + "\"");
            line_num++;
        }
    }
}
