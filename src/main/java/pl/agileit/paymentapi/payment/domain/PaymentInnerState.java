package pl.agileit.paymentapi.payment.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.user.UserId;
import pl.agileit.paymentapi.util.EntityVersion;

@RequiredArgsConstructor
@Builder(toBuilder = true)
public class PaymentInnerState {

	public final PaymentId id;

	public final PaymentAmount amount;

	public final PaymentCurrency currency;

	public final UserId userId;

	public final IBAN targetBankAccount;

	public final EntityVersion entityVersion;

}
