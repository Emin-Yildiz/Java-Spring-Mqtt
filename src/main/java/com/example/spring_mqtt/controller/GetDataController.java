package com.example.spring_mqtt.controller;

import com.example.spring_mqtt.beans.InConfiguration;
import com.example.spring_mqtt.repos.MqttRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mqtt")
public class GetDataController {

    @Autowired
    private MqttRepository mqttRepository;

    @GetMapping("/getData")
    public List<String> getData() {
        return mqttRepository.getMessages();
    }



}
