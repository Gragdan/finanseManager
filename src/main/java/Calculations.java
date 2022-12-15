import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Calculations {
    private HashMap<String, Long> sumCategory = new HashMap<>();
    private HashMap<String, String> categoryItems = new HashMap<>();

    public Calculations() throws IOException {
        this.sumCategory = sumCategory;
        this.categoryItems = categoryItems;
    }

    public void run() {
        File tsvFile = new File("categories.tsv");

        try (
                BufferedReader br = new BufferedReader(new FileReader(tsvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                categoryItems.put(parts[0], parts[1]);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


    public LocalDate parseData(String stringFromClient) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd");
        LocalDate date = LocalDate.parse(stringFromClient, formatter);
        return date;
    }

    public JSONObject maxCategory(String item, Long payment) {
        String category = categoryItems.get(item);
        if (categoryItems.get(item) == null) {
            category = "другое";
        }
        Long value = sumCategory.getOrDefault(category, 0L);
        Long summa = value + payment;
        sumCategory.put(category, summa);
        // находим ключ с максимальным значением
        String maxKey = sumCategory.keySet().stream()
                .max(Comparator.comparing(sumCategory::get))
                .orElse(null);
        // создаем объект и вносим полученные значения
        JSONObject jsonObject = new JSONObject();
        JSONObject jBody = new JSONObject();
        jBody.put("category", maxKey);
        jBody.put("sum", sumCategory.get(maxKey));
        jsonObject.put("maxCategory", jBody);
        return jsonObject;
    }

}
