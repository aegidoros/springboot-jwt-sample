package com.aer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.cas.jackson2.CasJackson2Module;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.jackson2.WebJackson2Module;

@SpringBootApplication
@Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//	@Bean
//	public Jackson2ObjectMapperBuilder jacksonBuilder() {
//		final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//		builder.indentOutput(true);
//		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		return builder;
//	}



    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
       // mapper.registerModules(SecurityJackson2Modules.getModules());
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        mapper.registerModule(new CoreJackson2Module());
//        mapper.registerModule(new CasJackson2Module());
//        mapper.registerModule(new WebJackson2Module());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

}


