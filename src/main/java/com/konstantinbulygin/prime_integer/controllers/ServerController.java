package com.konstantinbulygin.prime_integer.controllers;

import com.konstantinbulygin.prime_integer.model.ClientRequest;
import com.konstantinbulygin.prime_integer.model.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ServerController {

    private static boolean flag = false;

    @Autowired
    ServerResponse response;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/")
    public String admin() {
        return "index";
    }

    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        if (flag) {
            new Thread(() -> simpMessagingTemplate.convertAndSend("/topic/automessage", LocalDateTime.now())).start();
        }
    }

    @MessageMapping("/autoarray")
    public void autoArrays(ClientRequest request) {
        if (request.getClientMessage().equalsIgnoreCase("auto") && response.getMessages().size() > 0) {
            flag = true;
        }
    }

    @MessageMapping("/randarray")
    @SendTo("/topic/randmessage")
    public ServerResponse genArrays(ClientRequest request) {
        if (request.getClientMessage().equalsIgnoreCase("generate")) {
            if (response.getMessages().size() > 0) {
                ServerResponse serverResponse = new ServerResponse();
                List<List<Integer>> listArray = new ArrayList<>();
                evaluateRandomPrime(listArray);
                serverResponse.setMessages(listArray);
                return serverResponse;
            } else {
                return null;
            }
        }
        return null;
    }

    private void evaluateRandomPrime(List<List<Integer>> listArray) {
        for (int i = 0; i < response.getMessages().size(); i++) {
            Set<Integer> integerSet = new HashSet<>();
            Random r = new Random();
            while (integerSet.size() < 6) {
                integerSet.add(response.getMessages().get(i).get(r.nextInt(6) + 1));
            }
            List<Integer> integers = new ArrayList<>(integerSet);
            listArray.add(integers);
        }
    }

    @MessageMapping("/numarray")
    @SendTo("/topic/message")
    public ServerResponse getArrays(ClientRequest request) {
        try {
            int content = Integer.parseInt(request.getClientMessage());
            if (content >= 10 && content <= 100) {
                ServerResponse resp = generatePrimeArrays(content);
                response.setMessages(resp.getMessages());
                return resp;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private ServerResponse generatePrimeArrays(int len) {
        ServerResponse answer = new ServerResponse();
        int counter = 5;
        int startRange = 2;
        List<Integer> list = primeNumbersTill(startRange, len);

        while (counter > 0) {
            answer.getMessages().add(list);
            startRange = (list.get(list.size() - 1) + 2);
            list = primeNumbersTill(startRange, len);
            counter--;
        }
        return answer;
    }

    public static List<Integer> primeNumbersTill(int startRange, int n) {
        return IntStream.rangeClosed(startRange, Integer.MAX_VALUE)
                .filter(ServerController::isPrime).boxed()
                .limit(n)
                .collect(Collectors.toList());
    }

    private static boolean isPrime(int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .allMatch(n -> number % n != 0);
    }
}