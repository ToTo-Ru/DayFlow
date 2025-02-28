package cathy.chen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cathy.chen.Mapper")
public class NoteScheduleAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteScheduleAppBackendApplication.class, args);
    }

}
