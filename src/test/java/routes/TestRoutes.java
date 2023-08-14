package routes;

import com.driftkiller.EasyHttpInteraction;
import com.driftkiller.EasyRoute;
import com.driftkiller.HttpMethodType;
import model.Person;

import java.io.IOException;
import java.util.Map;

public class TestRoutes {


    @EasyRoute(path = "/test", httpMethodType = HttpMethodType.GET)
    public static void testRoute(EasyHttpInteraction interaction) throws IOException {

        //...perform some calculations in between before sending back a response

        interaction.send("OK");
    }

    @EasyRoute(path = "/serializetest", httpMethodType = HttpMethodType.GET)
    public static void serializeTestRoute(EasyHttpInteraction interaction) throws IOException {
        //this class is serializable by Gson
        Person person = new Person(
                "John",
                "Doe",
                "123 Placeholder Street",
                System.currentTimeMillis()
        );
        interaction.json(person);
    }

    @EasyRoute(path = "/echobody", httpMethodType = HttpMethodType.POST)
    public static void echoBodyRoute(EasyHttpInteraction interaction) throws IOException {
        Map<String, String> body = interaction.getBodyJson();
        interaction.json(body);
    }


}
