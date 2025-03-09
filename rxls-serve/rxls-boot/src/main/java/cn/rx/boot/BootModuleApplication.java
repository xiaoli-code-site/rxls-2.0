package cn.rx.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ComponentScan("cn.rx.*")
@MapperScan("cn.rx.db.mapper")
@EnableConfigurationProperties
public class BootModuleApplication {



    public static void main(String[] args) {
        SpringApplication.run(BootModuleApplication.class, args);
    }

}
