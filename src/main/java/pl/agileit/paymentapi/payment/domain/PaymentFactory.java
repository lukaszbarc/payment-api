package pl.agileit.paymentapi.payment.domain;

import pl.agileit.paymentapi.payment.domain.Payment;
import pl.agileit.paymentapi.payment.domain.PaymentInnerState;

public interface PaymentFactory {

	Payment create(PaymentInnerState toInnerState);
}
