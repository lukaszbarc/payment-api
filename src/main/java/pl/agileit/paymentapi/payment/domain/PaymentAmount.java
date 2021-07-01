package pl.agileit.paymentapi.payment.domain;

import java.math.BigDecimal;
import pl.agileit.paymentapi.util.SimpleId;

public class PaymentAmount extends SimpleId<BigDecimal> {

	protected PaymentAmount(BigDecimal value) {
		super(value);
	}

	public static PaymentAmount valueOf(BigDecimal value) {
		return new PaymentAmount(value);
	}

	public static PaymentAmount valueOf(String value) {
		return valueOf(new BigDecimal(value));
	}
}
