package pl.agileit.paymentapi.payment.domain;

import pl.agileit.paymentapi.util.SimpleId;

public class PaymentCurrency extends SimpleId<String> {

	protected PaymentCurrency(String value) {
		super(value);
	}

	public static PaymentCurrency valueOf(String value) {
		return new PaymentCurrency(value);
	}
}
