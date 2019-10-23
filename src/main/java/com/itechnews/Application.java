package com.itechnews;

import com.github.javafaker.Faker;
import com.itechnews.service.StorageProperties;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Locale;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({StorageProperties.class})
@EntityScan("com.itechnews.entity")
public class Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //do something when all beans has created
        System.out.println("Tomcat have started");
    }

    //bean file uploading
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //resolver.setMaxUploadSize(5242880);
        return resolver;
    }

    //bean data faker
    @Bean
    public Faker faker(){
        return new Faker(new Locale("vi"));
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}