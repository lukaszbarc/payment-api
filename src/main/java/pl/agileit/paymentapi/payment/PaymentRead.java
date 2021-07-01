package pl.agileit.paymentapi.payment;

import java.io.Serializable;
import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.payment.domain.PaymentAmount;
import pl.agileit.paymentapi.payment.domain.PaymentCurrency;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.user.UserId;
import pl.agileit.paymentapi.util.EntityVersion;

public interface PaymentRead extends Serializable {

	PaymentId getId();

	PaymentAmount getAmount();

	PaymentCurrency getCurrency();

	UserId getUserId();

	IBAN getTargetBankAccount();

	EntityVersion getVersion();
}
