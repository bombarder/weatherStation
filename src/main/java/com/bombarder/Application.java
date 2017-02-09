package com.bombarder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootApplication
public class Application {

    private final String weatherURL = "http://api.apixu.com/v1/current.json?key=cfdfcb3747f34ee5879155939170802&q=";

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {

            BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

            Runnable kiev = weatherRequest(queue, restTemplate, "Kiev");
            Runnable paris = weatherRequest(queue, restTemplate, "Paris");
            Runnable miami = weatherRequest(queue, restTemplate, "Miami");
            Runnable london = weatherRequest(queue, restTemplate, "London");
            Runnable newYork = weatherRequest(queue, restTemplate, "New_York");

            new Thread(kiev).start();
            new Thread(paris).start();
            new Thread(miami).start();
            new Thread(london).start();
            new Thread(newYork).start();

            double totalTemperature = 0;

            for (int i = 0; i < 5; i++) {
                Double tempValue = Double.parseDouble(queue.take());
                totalTemperature += tempValue;
            }
            System.out.println("The average temperature is: " + totalTemperature / 5);
        };
    }

    private Runnable weatherRequest(BlockingQueue<String> queue, RestTemplate restTemplate, String city) {
        return new Runnable() {
            @Override
            public void run() {
                Quote quote = restTemplate.getForObject(weatherURL + city, Quote.class);
                String temp = quote.getTempValue();
                System.out.println(city + ": " + temp);
                queue.add(temp);
            }
        };
    }
}
