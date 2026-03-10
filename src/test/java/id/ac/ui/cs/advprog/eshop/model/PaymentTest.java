package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentWithVoucherCode() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus("SUCCESS");
        payment.setPaymentData(this.paymentData);

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreatePaymentWithCashOnDelivery() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Margonda Raya No. 100");
        codData.put("deliveryFee", "15000");

        Payment payment = new Payment();
        payment.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        payment.setMethod("CASH_ON_DELIVERY");
        payment.setStatus("SUCCESS");
        payment.setPaymentData(codData);

        assertEquals("a0f9de46-90b1-437d-a0bf-d0821dde9096", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("Jl. Margonda Raya No. 100", payment.getPaymentData().get("address"));
        assertEquals("15000", payment.getPaymentData().get("deliveryFee"));
    }

    @Test
    void testCreatePaymentWithBankTransfer() {
        Payment payment = new Payment();
        payment.setId("x5f9de46-90b1-437d-a0bf-d0821dde9096");
        payment.setMethod("BANK_TRANSFER");
        payment.setStatus("SUCCESS");

        assertEquals("x5f9de46-90b1-437d-a0bf-d0821dde9096", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus("SUCCESS");
        payment.setPaymentData(this.paymentData);

        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testPaymentDataCanStoreMultipleKeyValuePairs() {
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        data.put("key3", "value3");

        Payment payment = new Payment();
        payment.setPaymentData(data);

        assertEquals(3, payment.getPaymentData().size());
        assertEquals("value1", payment.getPaymentData().get("key1"));
        assertEquals("value2", payment.getPaymentData().get("key2"));
        assertEquals("value3", payment.getPaymentData().get("key3"));
    }
}
