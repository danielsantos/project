package com.aplinotech.cadastrocliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SysEstoqueApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SysEstoqueApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SysEstoqueApplication.class, args);
	}
	
}
