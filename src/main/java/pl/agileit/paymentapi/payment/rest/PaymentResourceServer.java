package pl.agileit.paymentapi.payment.rest;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.agileit.paymentapi.payment.PaymentRead;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.payment.service.PaymentService;
import static pl.agileit.paymentapi.payment.rest.PaymentConverters.PAYMENT_CREATE_REQUEST_DTO_TO_COMMAND;
import static pl.agileit.paymentapi.payment.rest.PaymentConverters.PAYMENT_TO_PAYMENT_DTO;
import static pl.agileit.paymentapi.payment.rest.PaymentConverters.PAYMENT_UPDATE_REQUEST_DTO_TO_COMMAND;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
class PaymentResourceServer implements PaymentResource {

	private final PaymentService paymentService;

	@GetMapping
	@Override
	public Page<PaymentDTO> findPayments(Pageable pageable) {
		final Page<PaymentRead> payments = paymentService.findPayments(pageable);
		return payments.map(PAYMENT_TO_PAYMENT_DTO);
	}

	@PostMapping
	@Override
	public PaymentDTO createPayment(@RequestBody PaymentCreateRequestDTO dto) {
		final PaymentRead payment = paymentService.createPayment(PAYMENT_CREATE_REQUEST_DTO_TO_COMMAND.apply(dto));
		return PAYMENT_TO_PAYMENT_DTO.apply(payment);
	}

	@GetMapping("/{id}")
	@Override
	public PaymentDTO findPaymentById(@PathVariable("id") UUID paymentId) {
		final PaymentRead payment = paymentService.findPaymentById(PaymentId.valueOf(paymentId))
				.orElseThrow(() ->
						new ResponseStatusException(HttpStatus.NOT_FOUND)
				);
		return PAYMENT_TO_PAYMENT_DTO.apply(payment);
	}

	@PostMapping("/{id}")
	@Override
	public PaymentDTO updatePayment(@PathVariable("id") UUID paymentId, @RequestBody PaymentUpdateRequestDTO dto) {
		if (paymentId.equals(dto.id)) {
			final PaymentRead payment = paymentService.updatePayment(PAYMENT_UPDATE_REQUEST_DTO_TO_COMMAND.apply(dto))
					.orElseThrow(() ->
							new ResponseStatusException(HttpStatus.NOT_FOUND)
					);
			return PAYMENT_TO_PAYMENT_DTO.apply(payment);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	@Override
	public void deletePaymentById(@PathVariable("id") UUID paymentId) {
		paymentService.delete(
				PaymentId.valueOf(paymentId)
		);
	}
}
