import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static void main(String... args) {
        //Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("127.0.0.1", 8989);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String[] good = {"булка", "колбаса","сухарики","курица", "шапка", "мыло", "акции", "Маффин"};
            int[] expenses = {40, 50, 70, 170, 200, 230, 100, 300};
            Random rnd = new Random();
            int number = rnd.nextInt(8);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", good[number]);
            jsonObject.put("date", "2022.02.23");
            jsonObject.put("sum", expenses[number]);

            System.out.println(reader.readLine());

            writer.println(jsonObject);
            writer.flush();
            String serverWord = reader.readLine(); // ждём, что скажет сервер
            System.out.println(serverWord);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
