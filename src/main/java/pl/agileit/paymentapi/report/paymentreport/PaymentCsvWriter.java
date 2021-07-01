package pl.agileit.paymentapi.report.paymentreport;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.agileit.paymentapi.payment.domain.PaymentEvents;

@Slf4j
class PaymentCsvWriter extends Thread {

	private final BlockingDeque<PaymentEvents.PaymentUpdatedEvent> paymentUpdatedEventsQueue;

	private final BlockingDeque<PaymentEvents.PaymentCreatedEvent> paymentCreatedEventsQueue;

	PaymentCsvWriter(
			BlockingDeque<PaymentEvents.PaymentCreatedEvent> paymentCreatedEventsQueue,
			BlockingDeque<PaymentEvents.PaymentUpdatedEvent> paymentUpdatedEventsQueue) {
		this.paymentCreatedEventsQueue = paymentCreatedEventsQueue;
		this.paymentUpdatedEventsQueue = paymentUpdatedEventsQueue;
	}

	@Override
	public void run() {
		try {
			FileWriter out = new FileWriter("payments.csv");
			try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
				while (!Thread.interrupted()) {
					final PaymentEvents.PaymentCreatedEvent createdEvent = paymentCreatedEventsQueue.poll(10, TimeUnit.MICROSECONDS);
					if (createdEvent != null) {
						print(printer, createdEvent, "created");
					}
					final PaymentEvents.PaymentUpdatedEvent paymentUpdatedEvent = paymentUpdatedEventsQueue.poll(10, TimeUnit.MICROSECONDS);
					if (paymentUpdatedEvent != null) {
						print(printer, paymentUpdatedEvent, "updated");
					}
				}
			} finally {
				try {
					out.flush();
					out.close();
				} catch (IOException exception) {
					// ignore
				}
			}
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}

	}

	private void print(CSVPrinter printer, PaymentEvents.PaymentEvent createdEvent, String eventType) throws IOException {
		printer.printRecord(
				eventType,
				createdEvent.getPayment().getId().getRawValue(),
				createdEvent.getPayment().getAmount().getRawValue(),
				createdEvent.getPayment().getCurrency().getRawValue(),
				createdEvent.getPayment().getUserId().getRawValue(),
				createdEvent.getPayment().getTargetBankAccount().getRawValue()
		);
	}
}
