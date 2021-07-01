package pl.agileit.paymentapi.payment.infrastructure;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentDaoImpl extends JpaRepository<HibernatePayment, UUID> {

}
