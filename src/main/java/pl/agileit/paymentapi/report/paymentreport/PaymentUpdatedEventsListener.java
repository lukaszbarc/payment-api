package pl.agileit.paymentapi.report.paymentreport;

import java.util.concurrent.BlockingDeque;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.agileit.paymentapi.payment.domain.PaymentEvents.PaymentUpdatedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
class PaymentUpdatedEventsListener implements ApplicationListener<PaymentUpdatedEvent> {

	private final BlockingDeque<PaymentUpdatedEvent> paymentUpdatedEventsQueue;

	@Override
	public void onApplicationEvent(PaymentUpdatedEvent paymentUpdatedEvent) {
		paymentUpdatedEventsQueue.offer(paymentUpdatedEvent);
	}
}
