package com.example.spring_mqtt.beans;

import com.example.spring_mqtt.repos.MqttRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InConfiguration implements MqttRepository {

    ArrayList<String> messages = new ArrayList<>();

    @Bean
    IntegrationFlow inBoundFlow(MqttPahoMessageDrivenChannelAdapter inBoundAdapter) {
        return IntegrationFlows
                .from(inBoundAdapter)
                .handle((GenericHandler<String>) (payload, headers) -> {
                    System.out.println("New Message : " + payload);
                    messages.add(payload);
                    headers.forEach((key,value) -> System.out.println(key + "=  " + value));
                    return null;
                }).get();
    }

    @Bean
    MqttPahoMessageDrivenChannelAdapter inBoundAdapter(@Value("${hivemq.topic}") String topic, MqttPahoClientFactory clientFactory){
        return new MqttPahoMessageDrivenChannelAdapter("consumer",clientFactory,topic);
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

}
