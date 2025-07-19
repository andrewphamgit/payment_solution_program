package com.core.controller;

import com.core.serivce.PaymentService;
import com.core.utils.DateUtil;

import java.time.LocalDate;
import java.util.Arrays;

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController() {
        this.paymentService = new PaymentService(true);
    }

    public void processPayment(String commandLine) {
        String[] commands = commandLine.trim().split("\\s+");
        if (commands.length == 0) return;

        String action = commands[0].toUpperCase();
        try {
            switch (action) {
                case "CASH_IN":
                    long amount = Long.parseLong(commands[1]);
                    paymentService.cashIn(amount);
                    break;
                case "LIST_BILL":
                    paymentService.listBills();
                    break;
                case "PAY":
                    int[] billIds = Arrays.stream(Arrays.copyOfRange(commands, 1, commands.length))
                            .mapToInt(Integer::parseInt).toArray();
                    paymentService.pay(billIds);
                    break;
                case "DUE_DATE":
                    paymentService.listDueDateBills();
                    break;
                case "SCHEDULE":
                    int billId = Integer.parseInt(commands[1]);
                    LocalDate date = LocalDate.parse(commands[2], DateUtil.formatter);
                    paymentService.schedulePayment(billId, date);
                    break;
                case "LIST_PAYMENT":
                    paymentService.listPayments();
                    break;
                case "SEARCH_BILL_BY_PROVIDER":
                    String provider = commands[1];
                    paymentService.searchBillByProvider(provider);
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}
