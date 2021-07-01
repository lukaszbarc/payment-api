package pl.agileit.paymentapi.payment.rest;

import java.util.function.Function;
import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.payment.PaymentRead;
import pl.agileit.paymentapi.payment.domain.PaymentAmount;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentCreateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentCommands.PaymentUpdateCommand;
import pl.agileit.paymentapi.payment.domain.PaymentCurrency;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.user.UserId;
import pl.agileit.paymentapi.util.EntityVersion;

class PaymentConverters {

	static final  Function<PaymentRead, PaymentDTO> PAYMENT_TO_PAYMENT_DTO = paymentRead ->
			new PaymentDTO(
					paymentRead.getId().getRawValue(),
					paymentRead.getAmount().getRawValue(),
					paymentRead.getCurrency().getRawValue(),
					paymentRead.getUserId().getRawValue(),
					paymentRead.getTargetBankAccount().getRawValue(),
					paymentRead.getVersion().getRawValue()
			);

	static final Function<PaymentCreateRequestDTO, PaymentCreateCommand> PAYMENT_CREATE_REQUEST_DTO_TO_COMMAND = dto ->
			new PaymentCreateCommand(
					PaymentAmount.valueOf(dto.amount),
					PaymentCurrency.valueOf(dto.currency),
					UserId.valueOf(dto.userId),
					IBAN.valueOf(dto.targetBankAccount)
			);

	static final Function<PaymentUpdateRequestDTO, PaymentUpdateCommand> PAYMENT_UPDATE_REQUEST_DTO_TO_COMMAND = dto ->
			new PaymentUpdateCommand(
					PaymentId.valueOf(dto.id),
					PaymentAmount.valueOf(dto.amount),
					PaymentCurrency.valueOf(dto.currency),
					UserId.valueOf(dto.userId),
					IBAN.valueOf(dto.targetBankAccount),
					EntityVersion.valueOf(dto.version)
			);
}
