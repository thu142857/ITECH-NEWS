package com.itechnews.configuration;

import com.ckfinder.connector.ConnectorServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CKFinderServletConfig {

    private ClassLoader classLoader = CKFinderServletConfig.class.getClassLoader();

    private String ckfinderXMLPath = classLoader.getResource("static").getPath()
            + "/assets/public/lib/ckfinder_config.xml";

    @Bean
    public ServletRegistrationBean connectCKFinder() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ConnectorServlet(),
                "/assets/public/lib/ckfinder/core/connector/java/connector.java");
        System.out.println(ckfinderXMLPath);
        registrationBean.addInitParameter("XMLConfig", ckfinderXMLPath);
        registrationBean.addInitParameter("debug", "false");
        return registrationBean;
    }

}