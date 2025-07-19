package com.core.model;

import com.core.utils.DateUtil;

import java.time.LocalDate;

public class Payment {

    private int id;
    private long amount;
    private LocalDate paymentDate;
    private String state;
    private int billId;

    public Payment() {}

    public Payment(long amount, LocalDate paymentDate, String state, int billId) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.state = state;
        this.billId = billId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-10d %-12s %-12s %-8d",
                id, amount, DateUtil.formatDate(paymentDate), state, billId);
    }

}
