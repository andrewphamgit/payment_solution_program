package com.core.model;

import java.time.LocalDate;

public class ScheduledPayment {

    private long billId;
    private LocalDate scheduledDate;
    private String state;

    public ScheduledPayment() {}
    public ScheduledPayment(long billId, LocalDate scheduledDate, String state) {
        this.billId = billId;
        this.scheduledDate = scheduledDate;
        this.state = state;
    }

    public long getBillId() {
        return billId;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public String getState() {
        return state;
    }

}
