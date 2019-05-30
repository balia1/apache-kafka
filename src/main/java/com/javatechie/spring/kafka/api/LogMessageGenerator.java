package com.javatechie.spring.kafka.api;

import org.apache.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class LogMessageGenerator {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Scheduled(fixedDelay = 6000L)
    @EventListener(ApplicationReadyEvent.class)
    void logSomeStuff(){
        logger.info("Hello-from-Istanbul");
        logger.warn("Hello-from-Tokyo");
        logger.fatal("Hello-from-Moscow");
        logger.debug("Hello-from-Beijing");
        logger.error("Hello-from-London");

    }

}
