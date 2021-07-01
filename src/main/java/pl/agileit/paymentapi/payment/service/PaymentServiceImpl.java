package pl.agileit.paymentapi.payment.service;

import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.agileit.paymentapi.payment.PaymentRead;
import pl.agileit.paymentapi.payment.domain.Payment;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentCreateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentUpdateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentEvents.PaymentCreatedEvent;
import pl.agileit.paymentapi.payment.domain.PaymentEvents.PaymentUpdatedEvent;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.payment.domain.PaymentRepository;
import static java.util.function.Function.identity;

@Service
@RequiredArgsConstructor
@Transactional
class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Page<PaymentRead> findPayments(Pageable pageable) {
		return paymentRepository.findAll(pageable).map(identity());
	}

	@Override
	public void delete(PaymentId paymentId) {
		paymentRepository.delete(paymentId);
	}

	@Override
	public Optional<PaymentRead> findPaymentById(PaymentId paymentId) {
		return paymentRepository.findById(paymentId).map(identity());
	}

	@Override
	public Optional<PaymentRead> updatePayment(PaymentUpdateCommand command) {
		return paymentRepository.findById(command.getId())
				.map(payment ->
						paymentRepository.update(
								payment.update(command)
						)
				).map(payment -> {
					applicationEventPublisher.publishEvent(new PaymentUpdatedEvent(payment));
					return payment;
				});
	}

	@Override
	public PaymentRead createPayment(PaymentCreateCommand command) {
		final Payment payment = paymentRepository.save(
				Payment.create(command)
		);
		applicationEventPublisher.publishEvent(new PaymentCreatedEvent(payment));
		return payment;
	}
}
