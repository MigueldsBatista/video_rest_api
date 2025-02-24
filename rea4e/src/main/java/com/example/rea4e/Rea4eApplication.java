package com.example.rea4e;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example.rea4e")
public class Rea4eApplication {
    

    public static void main(String[] args) {
        SpringApplication.run(Rea4eApplication.class, args);

        System.out.print("\033[H\033[2J");//limpa o console

        System.out.println("Aplicação rodando...");

        System.out.println("Google Client ID: " + System.getenv("GOOGLE_CLIENT_ID"));
        System.out.println("Google Client Secret: " + System.getenv("GOOGLE_CLIENT_SECRET"));


    }

    
}

