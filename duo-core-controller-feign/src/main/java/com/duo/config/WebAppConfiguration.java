package com.duo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.SpringContextHolder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.ProgressListener;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@Slf4j
public class WebAppConfiguration extends WebMvcConfigurerAdapter {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {   
	  registry.addResourceHandler("/static/**")
              .addResourceLocations("classpath:/static/");//("file:/E:/static/");
      registry.addResourceHandler("/static2/**")
              .addResourceLocations("classpath:/static2/");//("file:/E:/static/");
      //swagger-ui
      registry.addResourceHandler("swagger-ui.html")
              .addResourceLocations("classpath:/META-INF/resources/");
      registry.addResourceHandler("/webjars/**")
              .addResourceLocations("classpath:/META-INF/resources/webjars/");

      registry.addResourceHandler("doc.html")
              .addResourceLocations("classpath:/META-INF/resources/");
      //在配置文件中配置的文件保存路径
      SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");
      log.info("filePath============="+sqlprop.getFilePath()+"\n");
      registry.addResourceHandler("/images/**").addResourceLocations(sqlprop.getFilePath());
      log.info("上传配置类filePath=="+sqlprop.getFilePath()+"\n");

      super.addResourceHandlers(registry);
  }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false); //支持后缀匹配
//        configurer.favorPathExtension(true)
//                .ignoreAcceptHeader(true)
//                .parameterName("mediaType")
//                .defaultContentType(MediaType.TEXT_HTML)
//                .mediaType("html", MediaType.TEXT_HTML)
//                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    /**
      * 修改自定义消息转化器
      * @param converters 消息转换器列表
      */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        //调用父类的配置
        super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //过滤并修改配置返回内容
        fastJsonConfig.setSerializerFeatures(
                        //List字段如果为null,输出为[],而非null
                        //SerializerFeature.WriteNullListAsEmpty,
                        //字符类型字段如果为null,输出为"",而非null
                        SerializerFeature.WriteNullStringAsEmpty,
                        //Boolean字段如果为null,输出为falseJ,而非null
                        //SerializerFeature.WriteNullBooleanAsFalse,
                        //消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
                        SerializerFeature.DisableCircularReferenceDetect,
                        //是否输出值为null的字段,默认为false。
                        SerializerFeature.WriteMapNullValue
                    );

        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastJsonConverter);
    } 

//    // 文件上传bean
//    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
//    public CommonsMultipartResolver commonsMultipartResolver() {
//        CommonsMultipartResolver resolver = new MyCommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8");
//        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//        resolver.setResolveLazily(true);
//        resolver.setMaxInMemorySize(40960);
//        //上传文件大小 50M 50*1024*1024
//        resolver.setMaxUploadSize(50*1024*1024);
//        return resolver;
//    }

//    public static class MyCommonsMultipartResolver extends CommonsMultipartResolver {
//        protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
//            FileUpload res = new ServletFileUpload(fileItemFactory);
//            res.setProgressListener(new ProgressListener() {
//                private long megaBytes = -1;
//
//                public void update(long pBytesRead, long pContentLength, int pItems) {
//                    // 控制频率
//                    long mBytes = pBytesRead / 1000000;
//                    if (megaBytes == mBytes) {
//                        return;
//                    }
//                    megaBytes = mBytes;
//                    log.info(String.format("正在读取第%s项...", pItems));
//                    log.info(String.format("共%s字节，已读取%s字节", pContentLength, pBytesRead));
//                }
//            });
//            return res;
//        }
//    }

//    @Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //文件最大KB,MB
//        factory.setMaxFileSize("20MB");
//        //设置总上传数据总大小
//        factory.setMaxRequestSize("20MB");
//        return factory.createMultipartConfig();
//    }
//


    /**
     * 主要配置多视图实现的视图解析器相关bean实例
     *
     *
     * 其实关键点在于两个：
     * 1、配置order属性
     * 2、配置viewnames属性
     *
     * 注意：
     * return new ModelAndView("jsps/index");//或者return "jsps/index"
     * 对应 /WEB-INF/jsps/index.jsp
     * ==========================
     * 同理：
     * return "thymeleaf/index";//或者return “thymeleaf/index”
     * 对应 /WEB-INF/thymeleaf/index.html
     *
     *
     */

    /**
     * SiteMesh配置
     * @return
     */
//    @Bean
//    public FilterRegistrationBean siteMeshFilter() {
//        FilterRegistrationBean fitler = new FilterRegistrationBean();
//        WebSiteMeshFilter siteMeshFilter = new WebSiteMeshFilter();
//        fitler.setFilter(siteMeshFilter);
//        return fitler;
//    }

    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlesourceView.class);
        resolver.setPrefix("/page/view/");
        resolver.setSuffix(".jsp");
        resolver.setViewNames("jsps/*");
        resolver.setOrder(1);
        return resolver;
    }

    /**
     * FreeMarker配置
     * @return
     */
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver  resolver = new FreeMarkerViewResolver ();
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setPrefix("/freemarker/");
        resolver.setSuffix(".ftl");
        resolver.setViewNames("ftl/*");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRequestContextAttribute("request");
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        resolver.setOrder(2);
        return resolver;
    }


    /**
     * html配置
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolverThymeLeaf() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("utf-8");
        viewResolver.setOrder(3);
        //viewResolver.setViewNames(new String[]{"thyme/*"});
        viewResolver.setViewNames(new String[]{"*","th/*","vue/*"});
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        // templateEngine
        return templateEngine;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }



    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    class JstlesourceView extends JstlView {
        @Override
        public boolean checkResource(Locale locale) {
            File file = new File(this.getServletContext().getRealPath("/") + getUrl());
            return file.exists();// 判断该页面是否存在
        }
    }

}  
