package com.vueadmin;

import com.vueadmin.web.netty.NewsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableWebMvc
public class VueAdminApplication implements CommandLineRunner
{
    @Autowired
    private NewsServer newsServer;

    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(VueAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

    @Override
    public void run(String... args) throws Exception {
        this.newsServer.start();
    }

}
