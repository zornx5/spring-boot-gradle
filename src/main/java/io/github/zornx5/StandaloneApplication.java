package io.github.zornx5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 独立的基础应用程序
 *
 * @author zornx5
 */
@SpringBootApplication
public class StandaloneApplication {

    /**
     * 程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(StandaloneApplication.class, args);
    }

}
