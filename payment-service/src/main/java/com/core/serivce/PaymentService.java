package com.core.serivce;

import com.core.model.Bill;
import com.core.model.Payment;
import com.core.model.ScheduledPayment;
import com.core.repository.AccountManager;
import com.core.utils.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PaymentService {

    private final AccountManager accountManager;

    public PaymentService(boolean hasDummyData) {
        this.accountManager = new AccountManager();

        if (hasDummyData) {
            accountManager.insertDummyDataBills();
        }
    }

    public void cashIn(long amount) {
        accountManager.cashIn(amount);
    }

    public void listBills() {
        List<Bill> bills = accountManager.getBills();
        System.out.printf("%-8s %-12s %-8s %-12s %-12s %-20s \n",
                "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER");
        bills.forEach(System.out::println);
    }

    public boolean pay(int[] billIds) {
        List<Bill> bills = accountManager.getBills();
        long currentBalance = this.getBalance();

        List<Bill> billsToPay = new ArrayList<>();
        long totalAmount = 0;
        for (int id : billIds) {
            Bill bill = bills.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
            if (bill == null) {
                System.out.println("Sorry! Not found a bill with such id " + id);
                return false;
            }

            if (!"PAID".equals(bill.getState())) {
                totalAmount += bill.getAmount();
                billsToPay.add(bill);
            }
        }

        if (totalAmount > currentBalance) {
            System.out.println("Sorry! Not enough fund to proceed with payment.");
            return false;
        }

        billsToPay.forEach(bill -> {
            accountManager.addPayment(new Payment(bill.getAmount(), LocalDate.now(), "PROCESSED", bill.getId()));
            accountManager.updatePaidBill(bill.getId());
            accountManager.cashIn(-bill.getAmount());
            System.out.println("Payment has been completed for Bill with id " + bill.getId());
        });

        System.out.println("Your current balance is: " + this.getBalance());
        return true;
    }

    public void listDueDateBills() {
        List<Bill> bills = accountManager.getBills();

        System.out.printf("%-8s %-12s %-8s %-12s %-12s %-20s \n",
                "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER");
        bills.stream().filter(b -> b.getState().equals("NOT_PAID"))
                .sorted(Comparator.comparing(Bill::getDueDate))
                .forEach(System.out::println);
    }

    public boolean schedulePayment(int billId, LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            System.out.println("Sorry! Cannot schedule the past.");
            return false;
        }

        List<Bill> bills = accountManager.getBills();
        Bill bill = bills.stream().filter(b -> b.getId() == billId).findFirst().orElse(null);
        if (bill == null) {
            System.out.println("Sorry! Not found a bill with such id " + billId);
            return false;
        }

        accountManager.addScheduledPayment(new ScheduledPayment(billId, date, "PENDING"));
        System.out.println("Payment for bill id " + billId + " is scheduled on " + DateUtil.formatDate(date));
        return true;
    }

    public List<Payment> listPayments() {
        List<Payment> payments = accountManager.getPayments();

        System.out.printf("%-5s %-10s %-12s %-12s %-8s \n",
                "No.", "Amount", "Payment Date", "State", "Bill Id");
        payments.forEach(System.out::println);

        return payments;
    }

    public List<Bill> searchBillByProvider(String provider) {
        List<Bill> bills = accountManager.getBills();

        bills = bills.stream().filter(b -> b.getProvider().equalsIgnoreCase(provider)).toList();
        if (bills.isEmpty()) {
            System.out.println("Sorry! Not found bills with such provider = " + provider);
            return Collections.emptyList();
        }

        System.out.printf("%-8s %-12s %-8s %-12s %-12s %-20s \n",
                "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER");
        bills.forEach(System.out::println);
        return bills;
    }

    public long getBalance() {
        return accountManager.getBalance();
    }

    public void addBill(Bill bill) {
        accountManager.addBill(bill);
    }

    public Bill getBillById(long id) {
        List<Bill> bills = accountManager.getBills();
        return bills.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public List<ScheduledPayment> listScheduledPayments() {
        return accountManager.getScheduledPayments();
    }

}
