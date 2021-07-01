package pl.agileit.paymentapi.bankaccount;

import pl.agileit.paymentapi.util.SimpleId;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

public class IBAN extends SimpleId<String> {

	protected IBAN(String value) {
		super(value);
		checkArgument(value.length() == 28, format("Invalid IBAN length (%d), valid length: 28", value.length()));

	}

	public static IBAN valueOf(String value) {
		return new IBAN(value);
	}
}
