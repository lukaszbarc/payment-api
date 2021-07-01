package pl.agileit.paymentapi.payment.rest;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

	public UUID id;

	public BigDecimal amount;

	public String currency;

	public UUID userId;

	public String targetBankAccount;

	public Long version;

}
