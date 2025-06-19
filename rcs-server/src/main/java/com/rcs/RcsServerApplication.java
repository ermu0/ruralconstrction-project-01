package com.rcs;

//import com.rcs.common.config.RcsCommonModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import({RcsCommonModuleConfig.class})
public class RcsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcsServerApplication.class, args);
    }

}
