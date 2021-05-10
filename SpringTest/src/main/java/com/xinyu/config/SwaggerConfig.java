package com.xinyu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaoxy
 */
@Configuration
@Slf4j
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${springfox.documentation.swagger-ui.enabled}")
    private boolean enable;
    /**
     * swagger3的配置文件
     */
    @Bean
    public Docket createRestApi() {
        log.info("=============加载Swagger3=============");
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                //这种是扫描所有注解的接口
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这种是扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xinyu.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalResponses(HttpMethod.GET, getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST, getGlobalResponseMessage())
                .globalResponses(HttpMethod.DELETE, getGlobalResponseMessage())
                .globalResponses(HttpMethod.PUT, getGlobalResponseMessage());



    }
    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     */
    private ApiInfo apiInfo() {
        // 获取工程名称
        return new ApiInfoBuilder()
                .title(appName + " API接口文档")
                .contact(new Contact("xinyu.jiao", "https://www.baidu.com", "1721591714@qq.com"))
                .version("1.0")
                .description("API文档")
                .build();
    }


    /**
     * 生成通用响应信息
     *
     * @return
     */
    private List<Response> getGlobalResponseMessage() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
        return responseList;
    }

}
