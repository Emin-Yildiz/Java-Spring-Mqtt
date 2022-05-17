package com.example.spring_mqtt.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class OutConfiguration {

    @Bean
    RouterFunction<ServerResponse> http(MessageChannel out){
        return route()
                .POST("/send/{value}", request -> {
                    var name = request.pathVariable("value");
                    var message= MessageBuilder.withPayload(name + "!").build();
                    out.send(message);
                    return ServerResponse.ok().build();
                })
                .build();
    }

    @Bean
    MqttPahoMessageHandler outBoundAdapter(@Value("${hivemq.topic}") String topic , MqttPahoClientFactory factory) {
        var mh = new MqttPahoMessageHandler("producer", factory);
        mh.setDefaultTopic(topic);
        return mh;
    }

    @Bean
    IntegrationFlow outboundFlow(MessageChannel out, MqttPahoMessageHandler outBoundAdapter) {
        return IntegrationFlows
                .from(out)
                .handle(outBoundAdapter)
                .get();
    }

    @Bean
    MessageChannel out() {
        return MessageChannels.direct().get();
    }

}
