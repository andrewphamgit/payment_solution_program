package com.core;

import com.core.model.Bill;
import com.core.model.Payment;
import com.core.serivce.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentAppTest {

    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        paymentService = new PaymentService(false);
    }

    @Test
    public void testCashIn() {
        paymentService.cashIn(500000);
        assertEquals(500000, paymentService.getBalance());
    }

    @Test
    public void testPayBill_Fail_NotFoundBill() {
        Bill bill = new Bill(1, "INTERNET", 500000, LocalDate.now().plusDays(1), "NOT_PAID", "VNPT");
        paymentService.addBill(bill);

        int[] billIds = {10};
        boolean result = paymentService.pay(billIds);
        assertFalse(result);

        Bill findBill = paymentService.getBillById(bill.getId());
        assertNotNull(findBill);
        assertEquals("NOT_PAID", findBill.getState());
    }

    @Test
    public void testPayBill_Fail_NotEnoughFund() {
        Bill bill = new Bill(1, "INTERNET", 500000, LocalDate.now().plusDays(1), "NOT_PAID", "VNPT");
        paymentService.addBill(bill);

        int[] billIds = {1};
        boolean result = paymentService.pay(billIds);
        assertFalse(result);

        Bill findBill = paymentService.getBillById(bill.getId());
        assertNotNull(findBill);
        assertEquals("NOT_PAID", findBill.getState());
    }


    @Test
    public void testPayBill_Success() {
        paymentService.cashIn(300000);
        Bill bill = new Bill(1, "WATER", 150000, LocalDate.now().plusDays(1), "NOT_PAID", "SAVACO");
        paymentService.addBill(bill);

        int[] billIds = {1};
        boolean result = paymentService.pay(billIds);
        assertTrue(result);
        assertEquals(150000, paymentService.getBalance());

        Bill findBill = paymentService.getBillById(bill.getId());
        assertNotNull(findBill);
        assertEquals("PAID", findBill.getState());

        List<Payment> payments = paymentService.listPayments();
        assertFalse(payments.isEmpty());
    }

    @Test
    public void testScheduledPayment_Fail_NotFoundBill() {
        Bill bill = new Bill(1, "WATER", 150000, LocalDate.now().plusDays(1), "NOT_PAID", "SAVACO");
        paymentService.addBill(bill);

        boolean result = paymentService.schedulePayment(2, LocalDate.now().plusDays(1));

        assertFalse(result);
        assertTrue(paymentService.listScheduledPayments().isEmpty());
    }

    @Test
    public void testScheduledPayment_Success() {
        Bill bill = new Bill(1, "WATER", 150000, LocalDate.now().plusDays(1), "NOT_PAID", "SAVACO");
        paymentService.addBill(bill);

        boolean result = paymentService.schedulePayment(bill.getId(), LocalDate.now().plusDays(1));

        assertTrue(result);
        assertFalse(paymentService.listScheduledPayments().isEmpty());
    }

    @Test
    public void testSearchByProvider_Success() {
        Bill bill = new Bill(1, "WATER", 150000, LocalDate.now().plusDays(1), "NOT_PAID", "SAVACO");
        paymentService.addBill(bill);

        List<Bill> searchBills = paymentService.searchBillByProvider("SAVACO");
        assertEquals(1, searchBills.size());
        assertEquals("SAVACO", searchBills.get(0).getProvider());
    }

}
