package com.example.spring_mqtt.repos;

import java.util.List;

public interface MqttRepository {

     List<String> getMessages();
}
