package tn.esprit.spring.Services;


import java.util.Date;
import java.util.List;
import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity. Amortization;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface AmortizationService {

    public void initializeUnknownFields(Amortization amortization);
    public List<Payment> calculatePaymentList(Date startDate, double initialBalance, int durationInMonths, int paymentType, double interestRate, double futureValue);
    public Date addOneMonth(Date date);
    public boolean createPdf(List<Payment> payments, ServletContext context, HttpServletRequest request,
                             HttpServletResponse response) ;


}
