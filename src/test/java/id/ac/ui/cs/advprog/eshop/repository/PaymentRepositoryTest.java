package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        payments = new ArrayList<>();

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment1 = new Payment(
                "eb558e9f-1c39-460e-8860-71af6af63bd6",
                "VOUCHER_CODE",
                PaymentStatus.SUCCESS.getValue(),
                voucherData
        );
        payments.add(payment1);

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Margonda Raya No. 100");
        codData.put("deliveryFee", "15000");

        Payment payment2 = new Payment(
                "a0f9de46-90b1-437d-a0bf-d0821dde9096",
                "CASH_ON_DELIVERY",
                PaymentStatus.PENDING.getValue(),
                codData
        );
        payments.add(payment2);

        Payment payment3 = new Payment(
                "x5f9de46-90b1-437d-a0bf-d0821dde9096",
                "BANK_TRANSFER",
                PaymentStatus.REJECTED.getValue(),
                new HashMap<>()
        );
        payments.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        Payment updatedPayment = new Payment(
                payment.getId(),
                payment.getMethod(),
                PaymentStatus.SUCCESS.getValue(),
                payment.getPaymentData()
        );
        Payment result = paymentRepository.save(updatedPayment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("nonexistent-id");
        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(3, paymentList.size());
        assertEquals(payments.get(0).getId(), paymentList.get(0).getId());
        assertEquals(payments.get(1).getId(), paymentList.get(1).getId());
        assertEquals(payments.get(2).getId(), paymentList.get(2).getId());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Payment> paymentList = paymentRepository.findAll();
        assertTrue(paymentList.isEmpty());
    }

    @Test
    void testSaveMultiplePayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(3, paymentList.size());
    }

    @Test
    void testUpdateExistingPaymentStatus() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }
}
