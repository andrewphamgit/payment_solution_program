package com.core.model;

import com.core.utils.DateUtil;

import java.time.LocalDate;

public class Bill {

    private int id;
    private String type;
    private long amount;
    private LocalDate dueDate;
    private String state;
    private String provider;

    public Bill() {}

    public Bill(int id, String type, long amount, LocalDate dueDate, String state, String provider) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    public int getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProvider() {
        return provider;
    }

    @Override
    public String toString() {
        return String.format("%-8d %-12s %-8d %-12s %-12s %-20s",
                id, type, amount, DateUtil.formatDate(dueDate), state, provider);
    }

}
