package pl.agileit.paymentapi.payment.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepository {

	Payment save(Payment command);

	Optional<Payment> findById(PaymentId id);

	Page<Payment> findAll(Pageable pageable);

	void delete(PaymentId paymentId);

	void deleteAll();

	Payment update(Payment command);
}
