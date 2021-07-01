package pl.agileit.paymentapi.report.paymentreport;

import java.util.concurrent.BlockingDeque;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agileit.paymentapi.payment.domain.PaymentEvents.PaymentCreatedEvent;
import pl.agileit.paymentapi.payment.domain.PaymentEvents.PaymentUpdatedEvent;

@Slf4j
@Service
class PaymentReportServiceImpl {

	private final PaymentCsvWriter paymentCsvWriter;

	@Autowired
	PaymentReportServiceImpl(BlockingDeque<PaymentCreatedEvent> paymentCreatedEventsQueue,
			BlockingDeque<PaymentUpdatedEvent> paymentUpdatedEventsQueue) {
		this.paymentCsvWriter = new PaymentCsvWriter(paymentCreatedEventsQueue, paymentUpdatedEventsQueue);
	}

	@PostConstruct
	void startCsvWriter() {
		paymentCsvWriter.start();
	}

	@PreDestroy
	void stopCsvWriter() {
		paymentCsvWriter.interrupt();
	}
}
