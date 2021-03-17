package net.croware.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import net.croware.showcase.backend.data.User;
import net.croware.showcase.backend.repositories.UserRepository;
import net.croware.showcase.backend.services.IUserService;

@EntityScan(basePackageClasses = { User.class, IUserService.class })
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
