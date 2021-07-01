package pl.agileit.paymentapi.payment.domain;

import java.io.Serializable;
import org.springframework.context.ApplicationEvent;
import pl.agileit.paymentapi.payment.PaymentRead;

public final class PaymentEvents {

	private PaymentEvents() {
	}

	public interface PaymentEvent {

		PaymentRead getPayment();
	}

	public static class PaymentCreatedEvent extends ApplicationEvent implements PaymentEvent, Serializable {

		private final PaymentRead payment;

		public PaymentCreatedEvent(PaymentRead payment) {
			super(payment);
			this.payment = payment;
		}

		@Override
		public PaymentRead getPayment() {
			return payment;
		}
	}

	public static class PaymentUpdatedEvent extends ApplicationEvent implements PaymentEvent, Serializable {

		private final PaymentRead payment;

		public PaymentUpdatedEvent(PaymentRead payment) {
			super(payment);
			this.payment = payment;
		}

		@Override
		public PaymentRead getPayment() {
			return payment;
		}
	}

}
