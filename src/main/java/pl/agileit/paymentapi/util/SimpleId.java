package pl.agileit.paymentapi.util;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Objects;

public abstract class SimpleId<T> implements Serializable {

	private final T value;

	protected SimpleId(T value) {
		this.value = Preconditions.checkNotNull(value);
	}

	public T getRawValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SimpleId)) {
			return false;
		}
		SimpleId<?> simpleId = (SimpleId<?>) o;
		return Objects.equals(value, simpleId.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
