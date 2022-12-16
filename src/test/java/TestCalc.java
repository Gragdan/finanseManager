import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class TestCalc {
    @Test
    public void test_ValidItem() throws IOException {
        Calculations calculations = new Calculations();
        calculations.run();
        String validItem = "булка";
        JSONObject jsonBody = (JSONObject) calculations.maxCategory(validItem, 100l).get("maxCategory");
        String actual = (String) jsonBody.get("category");
        String expected = "еда";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_OtherItem() throws IOException{
        Calculations calculations = new Calculations();
        calculations.run();
        String validItem = "что-то";// имя которого  нет в списке
        JSONObject jsonBody = (JSONObject) calculations.maxCategory(validItem, 100l).get("maxCategory");
        String actual = (String) jsonBody.get("category");
        String expected = "другое";

        Assertions.assertEquals(expected, actual);

    }
}
