package pl.agileit.paymentapi.payment.domain;

import java.util.UUID;
import pl.agileit.paymentapi.util.SimpleId;

public class PaymentId extends SimpleId<UUID> {

	protected PaymentId(UUID value) {
		super(value);
	}

	public static PaymentId valueOf(UUID uuid) {
		return new PaymentId(uuid);
	}

	public static PaymentId random() {
		return valueOf(UUID.randomUUID());
	}
}
