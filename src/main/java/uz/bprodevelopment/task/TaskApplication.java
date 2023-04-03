package uz.bprodevelopment.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.bprodevelopment.task.entity.base.Role;
import uz.bprodevelopment.task.entity.base.User;
import uz.bprodevelopment.task.service.base.RoleService;
import uz.bprodevelopment.task.service.base.UserService;

import java.util.*;

import static uz.bprodevelopment.task.config.Constants.*;

@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(
            UserService userService,
            RoleService roleService
    ) {
        return args -> {

            Role roleAdmin = roleService.getRole(ROLE_ADMIN);
            if (roleAdmin == null) {
                roleAdmin = roleService.saveRole(new Role(null, ROLE_ADMIN));
            }
            if (userService.getOneByUsername("admin") == null) {
                User user = userService.save(
                        new User(
                                null,
                                "Bunyod Xursanaliyev",
                                "admin",
                                "123",
                                null
                        ));
                userService.addRole(user.getId(), roleAdmin.getId());
            }


            Role roleManager = roleService.getRole(ROLE_MANAGER);
            if (roleManager == null) {
                roleManager = roleService.saveRole(new Role(null, ROLE_MANAGER));
            }
            if (userService.getOneByUsername("manager") == null) {
                User user = userService.save(
                        new User(
                                null,
                                "Alijon Valiyev",
                                "manager",
                                "123",
                                null
                        ));
                userService.addRole(user.getId(), roleManager.getId());
            }

        };
    }
}
