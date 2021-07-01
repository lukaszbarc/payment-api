package pl.agileit.paymentapi.payment.domain;

import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.payment.PaymentRead;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentCreateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentUpdateCommand;
import pl.agileit.paymentapi.user.UserId;
import pl.agileit.paymentapi.util.EntityVersion;

public class Payment implements PaymentRead {

	private PaymentInnerState paymentInnerState;

	public static Payment create(PaymentCreateCommand command) {
		return new Payment(new PaymentInnerState(
				PaymentId.random(),
				command.getAmount(),
				command.getCurrency(),
				command.getUserId(),
				command.getTargetBankAccount(),
				EntityVersion.valueOf(1L)
		));
	}

	Payment(PaymentInnerState paymentInnerState) {
		this.paymentInnerState = paymentInnerState;
	}

	public Payment update(PaymentUpdateCommand command) {
		this.paymentInnerState = this.paymentInnerState.toBuilder()
				.amount(command.getAmount())
				.currency(command.getCurrency())
				.userId(command.getUserId())
				.targetBankAccount(command.getTargetBankAccount())
				.entityVersion(command.getVersion())
				.build();
		return this;
	}

	@Override
	public PaymentId getId() {
		return paymentInnerState.id;
	}

	@Override
	public PaymentAmount getAmount() {
		return paymentInnerState.amount;
	}

	@Override
	public PaymentCurrency getCurrency() {
		return paymentInnerState.currency;
	}

	@Override
	public UserId getUserId() {
		return paymentInnerState.userId;
	}

	@Override
	public IBAN getTargetBankAccount() {
		return paymentInnerState.targetBankAccount;
	}

	@Override
	public EntityVersion getVersion() {
		return paymentInnerState.entityVersion;
	}
}
