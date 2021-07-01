package pl.agileit.paymentapi.payment.rest;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentResource {

	Page<PaymentDTO> findPayments(Pageable pageable);

	PaymentDTO createPayment(PaymentCreateRequestDTO dto);

	PaymentDTO findPaymentById(UUID paymentId);

	PaymentDTO updatePayment(UUID paymentId, PaymentUpdateRequestDTO dto);

	void deletePaymentById(UUID paymentId);
}
