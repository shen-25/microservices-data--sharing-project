package com.example;


import org.springframework.web.client.RestTemplate;

public class ApplicationTest {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <  100; i++) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://localhost:8081/actuator/sentinel", String.class);
            Thread.sleep(1900);
        }


    }

}