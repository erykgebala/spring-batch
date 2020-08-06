package pl.eryk.springbootbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JobConfig {

    
    @Bean
    Job job(JobBuilderFactory jbf, StepBuilderFactory sbf , Step1Configuration step1Configuration) {

        Step step1 = sbf.get("file-db")
                .<Person, Person> chunk(100)
                .reader(step1Configuration.fileReader())
                .writer(step1Configuration.jdbcWriter(null))
                .build();

   /*     Step step2 = sbf.get("do-to-file")
                .<Map<Integer, Integer>, Map<Integer, Integer>> chunk(100)
                .reader(null)
                .writer(null)
                .build();*/

        return jbf.get("etl")
                .incrementer(new RunIdIncrementer())
                .start(step1)
             //   .next(step2)
                .build();
    }
    
}
