package com.konstantinbulygin.prime_integer.controllers;

import com.konstantinbulygin.prime_integer.model.ServerAnswer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ServerController {

    @GetMapping("/")
    public String admin() {
        return "index";
    }


    @MessageMapping("/autoarrays")
    @SendTo("/topic/autoarrays")
    public ServerAnswer autoGenerateArrays(ServerAnswer answer, Model model) throws InterruptedException {
        Thread.sleep(1000);
        if (answer.getMessage() == 2) {
            model.addAttribute("auto", " auto generation");
            System.out.println(answer.getMessage() + " auto generation");
            return answer;
        } else {
            model.addAttribute("auto", " o generation");
            return answer;
        }
    }

    @MessageMapping("/genarrays")
    @SendTo("/topic/arrays")
    public ServerAnswer generateArrays(ServerAnswer answer) throws InterruptedException {
        Thread.sleep(1000);
        if (answer.getMessage() == 1) {
            System.out.println(answer.getMessage() + " simple generation");
            return answer;
        } else {
            return answer;
        }
    }

    @MessageMapping("/numarray")
    @SendTo("/topic/message")
    public ServerAnswer getArrays(ServerAnswer answer) throws InterruptedException {
        Thread.sleep(1000);
        if (answer.getMessage() > 0) {
            generatePrimeArrays(answer);
            return answer;
        } else {
            return answer;
        }
    }

    private void generatePrimeArrays(ServerAnswer answer) {
        int counter = 5;
        int startRange = 2;
        List<Integer> list = primeNumbersTill(startRange, answer.getMessage());

        while (counter > 0) {
            answer.getMessages().add(list);
            startRange = (list.get(list.size() - 1) + 2);
            list = primeNumbersTill(startRange, answer.getMessage());
            counter--;
        }
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