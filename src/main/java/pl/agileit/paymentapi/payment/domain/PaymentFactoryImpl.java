package pl.agileit.paymentapi.payment.domain;

import org.springframework.stereotype.Component;

@Component
class PaymentFactoryImpl implements PaymentFactory {

	@Override
	public Payment create(PaymentInnerState innerState) {
		return new Payment(innerState);
	}
}
