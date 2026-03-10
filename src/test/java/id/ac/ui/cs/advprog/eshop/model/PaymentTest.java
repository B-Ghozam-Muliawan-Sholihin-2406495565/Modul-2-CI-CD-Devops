package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("order-123", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentWithVoucherCode() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setOrder(order);
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        payment.setPaymentData(this.paymentData);

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
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
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        payment.setPaymentData(codData);

        assertEquals("a0f9de46-90b1-437d-a0bf-d0821dde9096", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("Jl. Margonda Raya No. 100", payment.getPaymentData().get("address"));
        assertEquals("15000", payment.getPaymentData().get("deliveryFee"));
    }

    @Test
    void testCreatePaymentWithBankTransfer() {
        Payment payment = new Payment();
        payment.setId("x5f9de46-90b1-437d-a0bf-d0821dde9096");
        payment.setMethod("BANK_TRANSFER");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertEquals("x5f9de46-90b1-437d-a0bf-d0821dde9096", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentUsingConstructor() {
        Payment payment = new Payment(
                "eb558e9f-1c39-460e-8860-71af6af63bd6",
                order,
                "VOUCHER_CODE",
                PaymentStatus.SUCCESS.getValue(),
                this.paymentData
        );

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        Payment payment = new Payment(
                "a0f9de46-90b1-437d-a0bf-d0821dde9096",
                order,
                "VOUCHER_CODE",
                PaymentStatus.REJECTED.getValue(),
                this.paymentData
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithPendingStatus() {
        Payment payment = new Payment(
                "a0f9de46-90b1-437d-a0bf-d0821dde9096",
                order,
                "CASH_ON_DELIVERY",
                PaymentStatus.PENDING.getValue(),
                this.paymentData
        );

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        payment.setPaymentData(this.paymentData);

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToPending() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        payment.setStatus(PaymentStatus.PENDING.getValue());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
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

    @Test
    void testCreatePaymentWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "eb558e9f-1c39-460e-8860-71af6af63bd6",
                    order,
                    "VOUCHER_CODE",
                    "INVALID_STATUS",
                    this.paymentData
            );
        });
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }

    @Test
    void testSetStatusWithLowercaseInvalid() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("success");
        });
    }

    @Test
    void testCreatePaymentWithRandomInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "a0f9de46-90b1-437d-a0bf-d0821dde9096",
                    order,
                    "CASH_ON_DELIVERY",
                    "COMPLETED",
                    this.paymentData
            );
        });
    }

    @Test
    void testSetStatusWithEmptyString() {
        Payment payment = new Payment();
        payment.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("");
        });
    }
}
