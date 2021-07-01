package pl.agileit.paymentapi.payment.infrastructure;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.payment.domain.PaymentAmount;
import pl.agileit.paymentapi.payment.domain.PaymentCurrency;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.payment.domain.PaymentInnerState;
import pl.agileit.paymentapi.user.UserId;
import pl.agileit.paymentapi.util.EntityVersion;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HibernatePayment {

	@Id
	@Column(updatable = false, nullable = false)
	private UUID id;

	@Setter
	@Column(nullable = false)
	private BigDecimal amount;

	@Setter
	@Column(nullable = false)
	private String currency;

	@Setter
	@Column(nullable = false)
	private UUID userId;

	@Setter
	@Column(nullable = false)
	private String targetBankAccount;

	@Setter
	@Version
	private Long version;

	PaymentInnerState toInnerState() {
		return new PaymentInnerState(
				PaymentId.valueOf(id),
				PaymentAmount.valueOf(amount),
				PaymentCurrency.valueOf(currency),
				UserId.valueOf(userId),
				IBAN.valueOf(targetBankAccount),
				EntityVersion.valueOf(version)
		);
	}
}
