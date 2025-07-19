package com.core;

import com.core.controller.PaymentController;

import java.util.Scanner;

public class PaymentApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentController processor = new PaymentController();

        System.out.println("Payment service started. Type your command");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Good bye!");
                break;
            }
            processor.processPayment(input);
        }
    }

}