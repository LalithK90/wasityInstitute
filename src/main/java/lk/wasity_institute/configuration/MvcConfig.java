package lk.wasity_institute.configuration;




@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/login");
        registry.addViewController("/login")
                .setViewName("login/login");
        registry.addViewController("/mainWindow")
                .setViewName("mainWindow");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

    }

    //Bean configuration
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/resources/templates/");
        resolver.setSuffix(".html");
        return resolver;
    }

    //time zone set to
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Colombo"));
    }

    //to enable Cache in spring boot
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public KeyGenerator multiplyKeyGenerator() {
        return (Object target, Method method, Object... params) -> method.getName() + "_" + Arrays.toString(params);
    }


    //Manage all errors
    @ControllerAdvice
    public static class ErrorController {

        private Logger logger = LoggerFactory.getLogger(ErrorController.class);

        @ExceptionHandler( Throwable.class )
        @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
        public String exception(final Throwable throwable, Model model) {
            logger.error("Exception during execution of SpringSecurity application", throwable);
            String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
            model.addAttribute("errorMessage", errorMessage);
            return "error/error";
        }
    }

}
