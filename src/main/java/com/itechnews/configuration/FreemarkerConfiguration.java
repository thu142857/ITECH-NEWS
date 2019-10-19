package com.itechnews.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FreemarkerConfiguration {
    /*Springboot supported => don't need to make this bean*/
   /*@Bean
    public freemarker.template.Configuration getFreeMarkerConfiguration() throws IOException, TemplateException {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("/templates/");
        return bean.createConfiguration();
    }*/
}