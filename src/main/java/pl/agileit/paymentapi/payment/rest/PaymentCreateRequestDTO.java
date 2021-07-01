package pl.agileit.paymentapi.payment.rest;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequestDTO {

	public BigDecimal amount;

	public String currency;

	public UUID userId;

	public String targetBankAccount;
}
