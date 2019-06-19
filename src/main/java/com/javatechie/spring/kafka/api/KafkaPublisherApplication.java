package com.javatechie.spring.kafka.api;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@EnableKafka
public class KafkaPublisherApplication {


	@Autowired
	SimpMessagingTemplate template;


	List<String> messages = new ArrayList<>();

	private String topic = "logg";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@KafkaListener(groupId = "logg", topics = "logg", containerFactory = "kafkaListenerContainerFactory")
	public List<String> getMsgFromTopic(String data) {
		messages.add(data);
		System.out.println("This is the data that you like "+data);
		template.convertAndSend("/topic/temperature", data);
		return messages;
	}
/*
	public void sendM(String message){
		logger.info("sending message='{}' to topic='{}'", message, topic);
		this.template.send(topic, message);

	}
*/


	public static void main(String[] args) {
		try {
			MDC.put("ip", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		SpringApplication.run(KafkaPublisherApplication.class, args);
	}
}
