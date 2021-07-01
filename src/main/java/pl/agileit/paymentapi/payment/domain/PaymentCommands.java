package pl.agileit.paymentapi.payment.domain;

import lombok.Value;
import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.user.UserId;
import pl.agileit.paymentapi.util.EntityVersion;

public class PaymentCommands {

	@Value
	public static class PaymentCreateCommand {

		PaymentAmount amount;

		PaymentCurrency currency;

		UserId userId;

		IBAN targetBankAccount;

	}

	@Value
	public static class PaymentUpdateCommand {

		PaymentId id;

		PaymentAmount amount;

		PaymentCurrency currency;

		UserId userId;

		IBAN targetBankAccount;

		EntityVersion version;

	}
}
