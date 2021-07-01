package pl.agileit.paymentapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.agileit.paymentapi.bankaccount.IBAN;
import pl.agileit.paymentapi.payment.domain.Payment;
import pl.agileit.paymentapi.payment.domain.PaymentAmount;
import pl.agileit.paymentapi.payment.domain.PaymentCommands;
import pl.agileit.paymentapi.payment.domain.PaymentCurrency;
import pl.agileit.paymentapi.payment.domain.PaymentId;
import pl.agileit.paymentapi.payment.domain.PaymentRepository;
import pl.agileit.paymentapi.payment.rest.PaymentCreateRequestDTO;
import pl.agileit.paymentapi.payment.rest.PaymentDTO;
import pl.agileit.paymentapi.payment.rest.PaymentUpdateRequestDTO;
import pl.agileit.paymentapi.user.UserId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class PaymentApiApplicationIntegrationTests {

	private static final PaymentCommands.PaymentCreateCommand SAMPLE_PAYMENT_CREATION_COMMAND =
			new PaymentCommands.PaymentCreateCommand(
					PaymentAmount.valueOf("66.66"),
					PaymentCurrency.valueOf("USD"),
					UserId.valueOf(UUID.randomUUID()),
					IBAN.valueOf("PL50102055581111167016700013")
			);

	private final ObjectMapper om = new ObjectMapper();

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() {
		paymentRepository.deleteAll();
	}

	@Test
	public void shouldCreatePayment() throws Exception {
		// when
		final PaymentDTO paymentDTO =
				om.readValue(
						mockMvc.perform(
								post("/payments")
										.contentType(MediaType.APPLICATION_JSON)
										.content(
												om.writeValueAsString(
														new PaymentCreateRequestDTO(
																SAMPLE_PAYMENT_CREATION_COMMAND.getAmount().getRawValue(),
																SAMPLE_PAYMENT_CREATION_COMMAND.getCurrency().getRawValue(),
																SAMPLE_PAYMENT_CREATION_COMMAND.getUserId().getRawValue(),
																SAMPLE_PAYMENT_CREATION_COMMAND.getTargetBankAccount().getRawValue()
														)
												)
										)
						)
								.andDo(print())
								.andExpect(status().isOk())
								.andReturn()
								.getResponse()
								.getContentAsString(),
						PaymentDTO.class
				);

		// then
		assertThat(paymentDTO.id).isNotNull();
		assertThat(paymentDTO.amount).isEqualTo(SAMPLE_PAYMENT_CREATION_COMMAND.getAmount().getRawValue());
		assertThat(paymentDTO.currency).isEqualTo(SAMPLE_PAYMENT_CREATION_COMMAND.getCurrency().getRawValue());
		assertThat(paymentDTO.userId).isEqualTo(SAMPLE_PAYMENT_CREATION_COMMAND.getUserId().getRawValue());
		assertThat(paymentDTO.targetBankAccount).isEqualTo(SAMPLE_PAYMENT_CREATION_COMMAND.getTargetBankAccount().getRawValue());
		assertThat(paymentDTO.version).isEqualTo(1L);
		assertThat(paymentRepository.findById(PaymentId.valueOf(paymentDTO.id))).isPresent();
	}

	@Test
	public void shouldFindPaymentById() throws Exception {
		// given
		final Payment payment = paymentRepository.save(Payment.create(SAMPLE_PAYMENT_CREATION_COMMAND));

		// when
		mockMvc.perform(get(String.format("/payments/%s", payment.getId().getRawValue())))
				.andDo(print())
				// then
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturn404InCasePaymentNotFound() throws Exception {
		// when
		mockMvc.perform(get(String.format("/payments/%s", UUID.randomUUID())))
				.andDo(print())
				// then
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnPageableListOfPayments() throws Exception {
		// given
		for (int i = 0; i < 50; i++) {
			paymentRepository.save(Payment.create(SAMPLE_PAYMENT_CREATION_COMMAND));
		}

		// when
		mockMvc.perform(get("/payments?page=2&size=5"))

				// then
				.andExpect(jsonPath("$.size", equalTo(5)))
				.andExpect(jsonPath("$.numberOfElements", equalTo(5)))
				.andExpect(jsonPath("$.totalElements", equalTo(50)))
				.andExpect(jsonPath("$.totalPages", equalTo(10)))
				.andExpect(jsonPath("$.content.length()", equalTo(5)))
				.andDo(print());
	}

	@Test
	public void shouldDeletePayment() throws Exception {
		// given
		final Payment payment = paymentRepository.save(Payment.create(SAMPLE_PAYMENT_CREATION_COMMAND));

		// when
		mockMvc.perform(
				delete(String.format("/payments/%s", payment.getId().getRawValue()))
		)
				.andDo(print())
				.andExpect(status().isOk());

		// then
		assertThat(paymentRepository.findById(payment.getId())).isNotPresent();
	}

	@Test
	public void shouldDeleteBeIdempotent() throws Exception {
		// given
		final Payment payment = paymentRepository.save(Payment.create(SAMPLE_PAYMENT_CREATION_COMMAND));

		// when
		mockMvc.perform(
				delete(String.format("/payments/%s", payment.getId().getRawValue()))
		)
				.andDo(print())
				.andExpect(status().isOk());

		mockMvc.perform(
				delete(String.format("/payments/%s", payment.getId().getRawValue()))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void shouldUpdatePayment() throws Exception {
		// given
		final Payment payment = paymentRepository.save(Payment.create(SAMPLE_PAYMENT_CREATION_COMMAND));

		// when
		mockMvc.perform(
				post(String.format("/payments/%s", payment.getId().getRawValue()))
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								om.writeValueAsString(
										new PaymentUpdateRequestDTO(
												payment.getId().getRawValue(),
												payment.getAmount().getRawValue().add(BigDecimal.TEN),
												"EUR",
												UUID.randomUUID(),
												"PL50102055581111169999999999",
												payment.getVersion().getRawValue()
										)
								)
						))
				.andDo(print())
				.andExpect(status().isOk());
		final Payment updated = paymentRepository.findById(payment.getId()).orElseThrow(IllegalStateException::new);

		// then
		assertThat(updated.getId()).isEqualTo(payment.getId());
		assertThat(updated.getAmount()).isEqualTo(PaymentAmount.valueOf(payment.getAmount().getRawValue().add(BigDecimal.TEN)));
		assertThat(updated.getCurrency()).isEqualTo(PaymentCurrency.valueOf("EUR"));
		assertThat(updated.getUserId()).isNotEqualTo(payment.getUserId());
		assertThat(updated.getTargetBankAccount()).isNotEqualTo(payment.getTargetBankAccount());
		assertThat(updated.getVersion().getRawValue()).isEqualTo(payment.getVersion().getRawValue() + 1L);
	}

}
