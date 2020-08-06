package pl.eryk.springbootbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@EnableBatchProcessing
@SpringBootApplication
public class SpringbootBatchApplication {

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

    @Bean
    Job job(JobBuilderFactory jbf, StepBuilderFactory sbf , ItemReader<Person> itemReader,
            ItemWriter<Person> itemWriter) {

        Step step1 = sbf.get("file-db")
                .<Person, Person> chunk(100)
                .reader(itemReader)
                .writer(itemWriter)
                .build();

        return jbf.get("etl")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringbootBatchApplication.class, args);
    }

    public static class Person {
        private int age;
        private String firstName;
        private String surname;
        private String email;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }
    }
}
