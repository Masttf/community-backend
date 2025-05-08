package fun.masttf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "fun.masttf" })
@MapperScan(basePackages = { "fun.masttf.mapper" })
@EnableTransactionManagement
public class communityWebApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(communityWebApplication.class, args);
    }
}
