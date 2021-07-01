package pl.agileit.paymentapi.payment.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.agileit.paymentapi.payment.PaymentRead;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentCreateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentUpdateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentId;

public interface PaymentService {

	PaymentRead createPayment(PaymentCreateCommand command);

	Page<PaymentRead> findPayments(Pageable pageable);

	void delete(PaymentId paymentId);

	Optional<PaymentRead> findPaymentById(PaymentId paymentId);

	Optional<PaymentRead> updatePayment(PaymentUpdateCommand command);
}
