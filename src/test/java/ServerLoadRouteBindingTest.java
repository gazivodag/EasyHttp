import com.driftkiller.EasyHttpServer;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import routes.TestRoutes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerLoadRouteBindingTest {

    @Test
    public void serverLoadAndRouteBinding() throws IOException {
        //initiate the server
        Class<?>[] routeClasses = new Class[]{
                TestRoutes.class
        };
        EasyHttpServer easyHttpServer = new EasyHttpServer(5196, routeClasses);
        easyHttpServer.start();

        //initiate the http client
        OkHttpClient client = new OkHttpClient();

        //make a get request to /test
        Request testRouteRequest = new Request.Builder()
                .url("http://127.0.0.1:5196/test")
                .build();
        Response testRouteResponse = client.newCall(testRouteRequest).execute();
        assert testRouteResponse.body() != null;
        System.out.println("Response from server after visiting /test: " + testRouteResponse.body().string());

        //make a get request to /serializetest
        Request serializeRouteRequest = new Request.Builder()
                .url("http://127.0.0.1:5196/serializetest")
                .build();
        Response serializeRouteResponse = client.newCall(serializeRouteRequest).execute();
        assert serializeRouteResponse.body() != null;
        System.out.println("Response from server after visiting /serializetest: " + serializeRouteResponse.body().string());

        //make a post request to /echobody
        Map<String, String> mapToSerialize = new HashMap<>() {{
            put("key1", "value1");
            put("key2", "value2");
            put("key3", "value3");
        }};
        String serializedMap = EasyHttpServer.GSON.toJson(mapToSerialize, Map.class);
        RequestBody echoRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), serializedMap);
        Request echoBodyRequest = new Request.Builder()
                .url("http://127.0.0.1:5196/echobody")
                .post(echoRequestBody)
                .build();
        Response echoBodyResponse = client.newCall(echoBodyRequest).execute();
        assert echoBodyResponse.body() != null;
        System.out.println("Response from server after visiting /echobody: " + echoBodyResponse.body().string());

        //stop server
        easyHttpServer.stop(0);
    }

}
