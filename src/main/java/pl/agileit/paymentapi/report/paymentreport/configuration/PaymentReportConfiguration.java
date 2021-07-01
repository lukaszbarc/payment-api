package pl.agileit.paymentapi.report.paymentreport.configuration;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agileit.paymentapi.payment.domain.PaymentEvents;

@Configuration
public class PaymentReportConfiguration {

	@Bean("paymentCreatedEventsQueue")
	BlockingDeque<PaymentEvents.PaymentCreatedEvent> paymentCreatedEventsQueue() {
		return new LinkedBlockingDeque<>(10_000);
	}

	@Bean("paymentUpdatedEventsQueue")
	BlockingDeque<PaymentEvents.PaymentUpdatedEvent> paymentUpdatedEventsQueue() {
		return new LinkedBlockingDeque<>(10_000);
	}
}
