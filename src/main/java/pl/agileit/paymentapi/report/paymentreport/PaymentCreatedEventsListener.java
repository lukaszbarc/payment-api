package pl.agileit.paymentapi.report.paymentreport;

import java.util.concurrent.BlockingDeque;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.agileit.paymentapi.payment.domain.PaymentEvents.PaymentCreatedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
class PaymentCreatedEventsListener implements ApplicationListener<PaymentCreatedEvent> {

	private final BlockingDeque<PaymentCreatedEvent> paymentCreatedEventsQueue;

	@Override
	public void onApplicationEvent(PaymentCreatedEvent paymentCreatedEvent) {
		paymentCreatedEventsQueue.offer(paymentCreatedEvent);
	}
}
