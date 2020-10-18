package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.example.CustomContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class CustomApplication {
    private static final Logger LOG = LoggerFactory.getLogger(CustomApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomApplication.class, args);

        invoke();
    }

    public static void invoke() {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 1; i <= 20; i++) {
            restTemplate.getForEntity("http://localhost:8080/index/" + i, String.class).getBody();
        }
    }

    @GetMapping("/index/{value}")
    public String index(@PathVariable(value = "value") String value) throws InterruptedException {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(value, "CustomContext");

        CustomContext.getCurrentContext().setAttributes(attributes);

        LOG.info("主线程：{}", CustomContext.getCurrentContext().getAttributes());

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("[Spleep之前] 子线程：{}", CustomContext.getCurrentContext().getAttributes());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LOG.info("[Spleep之后] 子线程：{}", CustomContext.getCurrentContext().getAttributes());
            }
        }).start();

        return "";
    }
}