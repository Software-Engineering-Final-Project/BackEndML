import java.util.ArrayList;


public class DataCleaner {

    public static ArrayList<Data> cleanData(ArrayList<Data> dataList) {
        for(int i = 0; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            updateCategory(data);
            formatDataFields(data);
        }

        return dataList;
    }

    public static ArrayList<Data> cleanData2(ArrayList<Data> dataList){
        for(int i = 0; i < dataList.size(); i++){
            Data data = dataList.get(i);
            updateCategory(data);
            formatDataFields2(data);
        }

        return dataList;
    }

    private static void updateCategory(Data data) {
        switch (data.getSource().getName()) {
            case "Ieee.org": data.setSourceName("IEEE"); break;
            case "Forbes.com": data.setSourceName("Forbes"); break;
            case "Nih.org": data.setSourceName("NIH"); break;
            case "Google News": data.setSourceName("Google News"); break;
            case "Newyorker.com": data.setSourceName("The New Yorker"); break;
            case "Webmd.com" : data.setSourceName("WebMD"); break;
            case "ABC News": data.setSourceName("ABC News"); break;
            case "USA Today": data.setSourceName("USA Today"); break;
            case "Psychologytoday.com": data.setSourceName("Psychology Today"); break;
            case "CNN": data.setSourceName("CNN"); break;
            case "Ted.com": data.setSourceName("TED"); break;
            case "Sciencemag.org": data.setSourceName("Science"); break;
            case "Scientificamerican.com": data.setSourceName("Scientific American"); break;
            default: data.setSourceName("Other"); break;
        }
    }


    private static void formatDataFields(Data data) {
        data.setTitle(formatField(data.getTitle()));
        data.setAuthor(formatField(data.getAuthor()));
        data.setDescription(formatField(data.getDescription()));
        data.setPublishedAt(formatField(data.getPublishedAt()));
        data.setUrl(formatField(data.getUrl()));
    }

    private static void formatDataFields2(Data data) {
        data.setTitle(formatField2(data.getTitle()));
        data.setAuthor(formatField2(data.getAuthor()));
        data.setDescription(formatField2(data.getDescription()));
        data.setPublishedAt(formatField2(data.getPublishedAt()));
        data.setUrl(formatField2(data.getUrl()));
    }


    private static String formatField (String description) {
        if(description == null) return "NULL";
        else {
            return description.replaceAll("\r\n", " ")
                    .replaceAll("\n", " ")
                    .replaceAll("\r", " ")
                    .replace("\"", "\\\"")
                    .replace(",", "\\");
        }
    }

    private static String formatField2 (String description) {
        if(description == null) return "NULL";
        else {
            return description.replaceAll("\r\n", " ")
                    .replaceAll("\n", " ")
                    .replaceAll("\r", " ")
                    .replace("\"", "\\\"");

        }
    }
}
