package pl.agileit.paymentapi.util;

public class EntityVersion extends SimpleId<Long> {

	protected EntityVersion(Long value) {
		super(value);
	}

	public static EntityVersion valueOf(Long version) {
		return new EntityVersion(version);
	}
}
