package cn.shizihuihui.ssweddingserver;

import cn.shizihuihui.ssweddingserver.entity.Bless;
import cn.shizihuihui.ssweddingserver.service.IBlessService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsweddingServerApplicationTests {

    @Autowired
    private IBlessService iBlessService;
    @Test
    public void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<Bless> list = iBlessService.list(null);
        Assert.assertEquals(1, list.size());
        list.forEach(System.out::println);
    }

}
