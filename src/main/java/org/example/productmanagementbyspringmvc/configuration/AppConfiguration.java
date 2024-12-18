package org.example.productmanagementbyspringmvc.configuration;


//import org.example.customermanagementthymeleaf.service.CustomerService;
import org.example.productmanagementbyspringmvc.service.IProductService;
import org.example.productmanagementbyspringmvc.service.ProductService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan("org.example.productmanagementbyspringmvc.controller")


@ComponentScan("org.example.productmanagementbyspringmvc.controller")
public class AppConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    // ... các code hiện tại giữ nguyên ...




    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    //// Template Resolver: Xác định vị trí các file HTML template
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }


    // Template Engine: Xử lý template sử dụng Template Resolver
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }


    // View Resolver: Liên kết Spring MVC với Thymeleaf để render view
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }


        @Bean
        public IProductService productService() {
            return new ProductService();
        }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/js/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("/WEB-INF/images/");
    }

}