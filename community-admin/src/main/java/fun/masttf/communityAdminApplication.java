package fun.masttf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = { "fun.masttf.mapper" })
@EnableTransactionManagement
public class communityAdminApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(communityAdminApplication.class, args);
    }
}
