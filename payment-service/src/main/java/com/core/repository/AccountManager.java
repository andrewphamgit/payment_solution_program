package com.core.repository;

import com.core.model.Bill;
import com.core.model.Payment;
import com.core.model.ScheduledPayment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to simulate database connection.
 */
public class AccountManager {

    private long balance;

    private int paymentCounter = 0;

    private final List<Bill> bills;
    private final List<Payment> payments;
    private final List<ScheduledPayment> scheduledPayments;

    public AccountManager() {
        balance = 0;
        payments = new ArrayList<>();
        scheduledPayments = new ArrayList<>();
        bills = new ArrayList<>();
    }

    public void insertDummyDataBills() {
        bills.add(new Bill(1, "ELECTRIC", 200000, LocalDate.of(2025, 7, 25), "NOT_PAID", "EVN HCMC"));
        bills.add(new Bill(2, "WATER", 175000, LocalDate.of(2025, 8, 15), "NOT_PAID", "SAVACO HCMC"));
        bills.add(new Bill(3, "INTERNET", 800000, LocalDate.of(2025, 7, 30), "NOT_PAID", "VNPT"));
    }

    public void cashIn(long amount) {
        balance += amount;
        System.out.println("Your available balance: " + balance);
    }

    public long getBalance() {
        return balance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public void updatePaidBill(int billId) {
        bills.forEach(b -> {
            if (b.getId() == billId) b.setState("PAID");
        });
    }

    public void addPayment(Payment payment) {
        payment.setId(++paymentCounter);
        payments.add(payment);
    }

    public void addScheduledPayment(ScheduledPayment payment) {
        scheduledPayments.add(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<ScheduledPayment> getScheduledPayments() {
        return scheduledPayments;
    }

}
