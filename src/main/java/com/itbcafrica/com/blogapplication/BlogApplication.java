package com.itbcafrica.com.blogapplication;

import com.itbcafrica.com.blogapplication.entity.Role;
import com.itbcafrica.com.blogapplication.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
@EnableWebMvc
public class BlogApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

    }
}
