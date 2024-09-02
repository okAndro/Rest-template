package org.task314;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.task314.model.User;
import org.task314.service.UserService;

@SpringBootApplication
public class Task314Application {
    public static void main(String[] args) {
        SpringApplication.run(Task314Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(ApplicationContext context) {
        return args -> {
            UserService userService = context.getBean(UserService.class);

            // Получение всех пользователей
            userService.getAllUsers().forEach(System.out::println);

            // Сохранение пользователя
            String codePart1 = userService.saveUser(new User(3L, "James", "Brown", (byte) 30));
            System.out.println("Code Part 1: " + codePart1);

            // Обновление пользователя
            String codePart2 = userService.updateUser(new User(3L, "Thomas", "Shelby", (byte) 30));
            System.out.println("Code Part 2: " + codePart2);

            // Удаление пользователя
            String codePart3 = userService.deleteUser(3L);
            System.out.println("Code Part 3: " + codePart3);

            // Конкатенация всех частей кода
            String finalCode = codePart1 + codePart2 + codePart3;
            System.out.println("Final Code: " + finalCode);
            System.out.println(finalCode.length() + " = 18 так что все четко"  );
        };
    }
}