package pl.agileit.paymentapi.payment.infrastructure;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.agileit.paymentapi.payment.domain.Payment;
import pl.agileit.paymentapi.payment.domain.PaymentFactory;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.payment.domain.PaymentRepository;

@Repository
@RequiredArgsConstructor
class PaymentRepositoryImpl implements PaymentRepository {

	private final SpringDataPaymentDaoImpl springDataPaymentDao;

	private final PaymentFactory paymentFactory;

	@Override
	public void deleteAll() {
		springDataPaymentDao.deleteAll();
	}

	@Override
	public Payment update(Payment payment) {
		final HibernatePayment hibernatePayment = springDataPaymentDao.getById(payment.getId().getRawValue());
		hibernatePayment.setAmount(payment.getAmount().getRawValue());
		hibernatePayment.setCurrency(payment.getCurrency().getRawValue());
		hibernatePayment.setTargetBankAccount(payment.getTargetBankAccount().getRawValue());
		hibernatePayment.setUserId(payment.getUserId().getRawValue());
		hibernatePayment.setVersion(payment.getVersion().getRawValue());
		return paymentFactory.create(hibernatePayment.toInnerState());
	}

	@Override
	public Payment save(Payment payment) {
		final HibernatePayment hibernatePayment = springDataPaymentDao.save(
				new HibernatePayment(
						payment.getId().getRawValue(),
						payment.getAmount().getRawValue(),
						payment.getCurrency().getRawValue(),
						payment.getUserId().getRawValue(),
						payment.getTargetBankAccount().getRawValue(),
						1L
				)
		);
		return paymentFactory.create(hibernatePayment.toInnerState());
	}

	@Override
	public Optional<Payment> findById(PaymentId id) {
		return springDataPaymentDao.findById(id.getRawValue())
				.map(payment ->
						paymentFactory.create(
								payment.toInnerState()
						)
				);
	}

	@Override
	public Page<Payment> findAll(Pageable pageable) {
		return springDataPaymentDao.findAll(pageable)
				.map(payment ->
						paymentFactory.create(
								payment.toInnerState()
						)
				);
	}

	@Override
	public void delete(PaymentId paymentId) {
		springDataPaymentDao.findById(paymentId.getRawValue())
				.ifPresent(
						hibernatePayment ->
								springDataPaymentDao.deleteById(
										paymentId.getRawValue()
								));
	}
}
