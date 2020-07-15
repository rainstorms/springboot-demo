package rain.mergeRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class controllerTest2 {

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void queryResponseById() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://localhost:8081/rain-server/hello/queryResponseById/1";
                String result = template.getForObject(url, String.class);
                System.out.println(result);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://localhost:8081/rain-server/hello/queryResponseById/2";
                String result = template.getForObject(url, String.class);
                System.out.println(result);
            }
        }).start();

    }
}