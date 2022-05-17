package com.example.spring_mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

@SpringBootApplication
public class SpringMqttApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMqttApplication.class, args);
	}

	@Bean
	MqttPahoClientFactory clientFactory(@Value("${hivemq.uri}") String host){
		var factory  = new DefaultMqttPahoClientFactory();
		var options = new MqttConnectOptions();
		options.setServerURIs(new String[]{host});
		factory.setConnectionOptions(options);
		return factory;
	}

}
