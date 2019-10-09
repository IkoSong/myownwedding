package cn.shizihuihui.ssweddingserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.shizihuihui.ssweddingserver.mapper")
public class SsweddingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsweddingServerApplication.class, args);
    }

}
