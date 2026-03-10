package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setOrder(order);
        payment.setMethod(method);
        
        if ("VOUCHER_CODE".equals(method) && paymentData != null) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucherCode(voucherCode)) {
                payment.setStatus(PaymentStatus.SUCCESS.getValue());
                if (order != null) {
                    order.setStatus(OrderStatus.SUCCESS.getValue());
                    orderRepository.save(order);
                }
            } else {
                payment.setStatus(PaymentStatus.REJECTED.getValue());
                if (order != null) {
                    order.setStatus(OrderStatus.FAILED.getValue());
                    orderRepository.save(order);
                }
            }
        } else {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
            if (order != null) {
                order.setStatus(OrderStatus.FAILED.getValue());
                orderRepository.save(order);
            }
        }
        
        payment.setPaymentData(paymentData);
        
        paymentRepository.save(payment);
        return payment;
    }
    
    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }
        
        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }
        
        long digitCount = voucherCode.chars().filter(Character::isDigit).count();
        return digitCount == 8;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        paymentRepository.save(payment);
        
        if (PaymentStatus.SUCCESS.getValue().equals(status)) {
            Order order = payment.getOrder();
            if (order != null) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
                orderRepository.save(order);
            }
        } else if (PaymentStatus.REJECTED.getValue().equals(status)) {
            Order order = payment.getOrder();
            if (order != null) {
                order.setStatus(OrderStatus.FAILED.getValue());
                orderRepository.save(order);
            }
        }
        
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
