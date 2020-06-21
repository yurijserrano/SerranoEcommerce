package com.yjsserrano.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Here will be where the Serrano {@link EcommerceApplication} will start
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://springbootdev.com/2017/11/13/what-are-the-uses-of-entityscan-and-enablejparepositories-annotations/">@EnableJpaRepositories I</a>
 * @see <a href="https://stackoverflow.com/questions/45261791/enablejparepositories-looking-for-which-package">@EnableJpaRepositories II</a>
 * @see <a href="https://dzone.com/articles/spring-data-jpa-1">@EnableJpaRepositories III</a>
 * @see <a href="https://github.com/spring-projects/spring-boot/issues/18109">Scan Base Packages I</a>
 * @see <a href="https://www.java2novice.com/issues/spring-boot-not-scanning-components/">Scan Base Packages II</a>
 * @see <a href="https://stackoverflow.com/questions/38896414/difference-between-entityscan-and-componentscan">@ComponentScan vs @EntityScan</a>
 * @see <a href="https://dzone.com/articles/a-guide-to-spring-framework-annotations">Annotations I</a>
 * @see <a href="https://reflectoring.io/spring-boot-modules/">Annotations II</a>
 * @see <a href="http://zetcode.com/springboot/annotations/">Annotations III</a>
 * @see <a href="https://howtodoinjava.com/spring5/core/spring-bean-java-config/">Annotations IV</a>
 * @see <a href="https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring">IoC and DI I</a>
 * @see <a href="https://www.boraji.com/spring-4-qualifier-annotation-example">IoC and DI II</a>
 * @see <a href="http://zetcode.com/springboot/bean/">@Bean</a>
 * @see <a href="https://www.springboottutorial.com/spring-boot-and-component-scan">@ComponentScan</a>
 * @see <a href="https://howtodoinjava.com/spring-core/spring-configuration-annotation/">@Configuration I</a>
 * @see <a href="https://www.javaguides.net/2018/09/spring-configuration-annotation-with-example.html">@Configuration II</a>
 * @see <a href="https://examples.javacodegeeks.com/enterprise-java/spring/spring-configuration-annotation-example/">@Configuration II</a>
 * @since 1.0
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.yjsserrano.ecommerce.domain"})
@EnableJpaRepositories(basePackages = {"com.yjsserrano.ecommerce.repository"})
public class EcommerceApplication {

    private static final Logger log = LoggerFactory.getLogger("splunk.logger");

    /**
     * Here is where the application starts
     *
     * @param args {@link String[]}
     */
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
        log.info("Serrano Ecommerce Application - Initiated");
    }

    /**
     * Here the {@link BCryptPasswordEncoder} is instantiated and can be used in other parts of the application
     *
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
