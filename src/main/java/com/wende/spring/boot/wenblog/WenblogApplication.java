package com.wende.spring.boot.wenblog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
//@MapperScan("com.wende.spring.boot.wenblog.dao.user")
public class WenblogApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WenblogApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WenblogApplication.class, args);
    }

}

//使用tomcat部署需要配置虚拟路径加载图片
@Configuration
class MyPicConfig implements WebMvcConfigurer {
    @Value("${wenblog.user.default.upload.dir}")
    String defaultUploadDir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!defaultUploadDir.equals("relativePath")){
            registry.addResourceHandler("/upload/**").addResourceLocations("file:"+defaultUploadDir+"upload/");
        }
    }
}


