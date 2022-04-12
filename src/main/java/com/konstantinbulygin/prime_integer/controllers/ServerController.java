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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ServerController {

    private boolean flag = false;

    @Autowired
    ServerResponse response;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/")
    public String admin() {
        return "index";
    }

    //send prime arrays periodically
    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        if (flag) {
            new Thread(() ->
                    simpMessagingTemplate
                            .convertAndSend("/topic/automessage", evaluateOneRandomArray())).start();
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
                //evaluate random arrays from all that present
                evaluateRandomPrimeArrays(listArray);
                serverResponse.setMessages(listArray);
                return serverResponse;
            } else {
                return null;
            }
        }
        return null;
    }

    @MessageMapping("/numarray")
    @SendTo("/topic/message")
    public ServerResponse getArrays(ClientRequest request) {
        try {
            int content = Integer.parseInt(request.getClientMessage());
            if (content >= 10 && content <= 100) {
                ServerResponse resp = getAndSendPrimeArrays(content);
                response.setMessages(resp.getMessages());
                return resp;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    //get prime arrays
    private ServerResponse getAndSendPrimeArrays(int len) {
        ServerResponse answer = new ServerResponse();
        int counter = 5;
        int startRange = 2;
        List<Integer> list = generatePrimeNumbers(startRange, len);

        while (counter > 0) {
            answer.getMessages().add(list);
            startRange = (list.get(list.size() - 1) + 2);
            list = generatePrimeNumbers(startRange, len);
            counter--;
        }
        return answer;
    }

    //evaluate random arrays from 2 till Integer.MAX_VALUE
    public static List<Integer> generatePrimeNumbers(int startRange, int n) {
        return IntStream.rangeClosed(startRange, Integer.MAX_VALUE)
                .filter(ServerController::isPrime).boxed()
                .limit(n)
                .collect(Collectors.toList());
    }

    //check the number is prime
    private static boolean isPrime(int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .allMatch(n -> number % n != 0);
    }

    //evaluate one random array from all that present periodically
    private List<Integer> evaluateOneRandomArray() {
        Set<Integer> integerSet = new HashSet<>();
        int sizeOfLists = response.getMessages().size();
        int len = 6;

        for (int i = 0; i < sizeOfLists; i++) {
            Random random = new Random();
            while (integerSet.size() < len) {
                //one list of integers
                var integers = response.getMessages().get(i);
                //find random number from list and add to set
                integerSet.add(integers.get(random.nextInt(getBoundaries(i)) + 1));
            }
        }
        return new ArrayList<>(integerSet);
    }

    //evaluate random arrays from all that present
    private void evaluateRandomPrimeArrays(List<List<Integer>> listArray) {
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

    //evaluate bounds
    private int getBoundaries(int i) {
        return response.getMessages().get(i).size() - 1;
    }
}