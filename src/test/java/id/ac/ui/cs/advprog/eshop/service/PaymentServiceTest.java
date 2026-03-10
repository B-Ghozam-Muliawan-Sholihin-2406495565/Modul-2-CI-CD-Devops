package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    Order order;
    Payment payment;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment();
        payment.setId("payment-123");
        payment.setOrder(order);
        payment.setMethod("VOUCHER_CODE");
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        payment.setPaymentData(paymentData);
    }

    @Test
    void testAddPayment() {
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
        assertNotNull(result.getId());
        assertEquals(order, result.getOrder());
        assertEquals("VOUCHER_CODE", result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        assertEquals(paymentData, result.getPaymentData());
    }

    @Test
    void testAddPaymentWithValidVoucherCode() {
        Map<String, String> validVoucher = new HashMap<>();
        validVoucher.put("voucherCode", "ESHOP1234ABC5678");
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", validVoucher);

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithInvalidVoucherCodeLength() {
        Map<String, String> invalidVoucher = new HashMap<>();
        invalidVoucher.put("voucherCode", "ESHOP123");
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", invalidVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithInvalidVoucherCodePrefix() {
        Map<String, String> invalidVoucher = new HashMap<>();
        invalidVoucher.put("voucherCode", "SHOP12345ABC5678");
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", invalidVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucherCodeDigitCount() {
        Map<String, String> invalidVoucher = new HashMap<>();
        invalidVoucher.put("voucherCode", "ESHOP123ABCDEFGH");
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", invalidVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithNonVoucherMethod() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Margonda");
        codData.put("deliveryFee", "15000");
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", codData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithBankTransfer() {
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", new HashMap<>());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithNullVoucherCode() {
        Map<String, String> nullVoucher = new HashMap<>();
        nullVoucher.put("voucherCode", null);
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", nullVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithEmptyVoucherCode() {
        Map<String, String> emptyVoucher = new HashMap<>();
        emptyVoucher.put("voucherCode", "");
        
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", emptyVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToSuccess() {
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToRejected() {
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "INVALID_STATUS"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusWithPendingStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "PENDING"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccessWhenOrderNotFound() {
        payment.setOrder(null);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testGetPayment() {
        doReturn(payment).when(paymentRepository).findById("payment-123");

        Payment result = paymentService.getPayment("payment-123");

        assertEquals(payment.getId(), result.getId());
        verify(paymentRepository, times(1)).findById("payment-123");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        Payment payment2 = new Payment();
        payment2.setId("payment-456");
        payment2.setMethod("BANK_TRANSFER");
        payment2.setStatus(PaymentStatus.SUCCESS.getValue());
        payments.add(payment2);

        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
        assertEquals(payment2.getId(), result.get(1).getId());
        verify(paymentRepository, times(1)).findAll();
    }
}
