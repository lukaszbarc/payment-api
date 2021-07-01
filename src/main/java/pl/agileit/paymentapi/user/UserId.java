package pl.agileit.paymentapi.user;

import java.util.UUID;
import pl.agileit.paymentapi.util.SimpleId;

public class UserId extends SimpleId<UUID> {

	protected UserId(UUID value) {
		super(value);
	}

	public static UserId valueOf(UUID value) {
		return new UserId(value);
	}
}
