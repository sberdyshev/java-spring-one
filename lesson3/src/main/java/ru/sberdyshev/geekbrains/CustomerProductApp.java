package ru.sberdyshev.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberdyshev.geekbrains.config.AppConfig;
import ru.sberdyshev.geekbrains.controller.CLIController;
import ru.sberdyshev.geekbrains.controller.CustomerProductCLIController;

public class CustomerProductApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CLIController cliController = context.getBean("customerProductCLIController", CustomerProductCLIController.class);
        cliController.start();
    }
}
