package pl.eryk.springbootbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@EnableBatchProcessing
@SpringBootApplication
public class SpringbootBatchApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringbootBatchApplication.class, args);
    }
}
