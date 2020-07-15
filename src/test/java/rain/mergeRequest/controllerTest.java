package rain.mergeRequest;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

//https://www.cnblogs.com/xiufengchen/p/10835040.html
@SpringBootTest
@RunWith(SpringRunner.class)
public class controllerTest {

    @Autowired
    public RedisTemplate redis;

    @Test
    public void contextLoads() {
    }

    //引入 ContiPerf 进行性能测试
    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    @Test
    //10个线程 执行10次
    @PerfTest(invocations = 100, threads = 10)
    public void test() {
        long id = Thread.currentThread().getId();
        redis.opsForValue().set("aa", id + "");
        System.out.println(id);

    }
}