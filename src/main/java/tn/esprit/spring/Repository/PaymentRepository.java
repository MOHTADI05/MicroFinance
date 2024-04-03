package tn.esprit.spring.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
     Payment findByPaymentNumber(int paymentNumber) ;

    // Ajoutez des méthodes personnalisées du repository si nécessaire

}
