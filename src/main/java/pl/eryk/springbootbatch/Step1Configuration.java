package pl.eryk.springbootbatch;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class Step1Configuration {
    @Bean
    FlatFileItemReader<Person> fileReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("file-reader")
                .resource(new ClassPathResource("in.csv"))
                .targetType(Person.class)
                .delimited().delimiter(",").names(new String[]{ "firstName", "surname", "age", "emial"})
                .build();
    }

    @Bean
    JdbcBatchItemWriter<Person> jdbcWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataSource)
                .sql("insert into PEOPLE(AGE, FIRST_NAME, SURNAME, EMAIL) VALUES (:age, :firstName, :surname, :email)")
                .beanMapped()
                .build();
    }

}
