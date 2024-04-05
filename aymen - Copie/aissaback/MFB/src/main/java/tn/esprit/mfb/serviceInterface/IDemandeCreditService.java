package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Amortissement;
import tn.esprit.mfb.entity.DemandeCredit;
import tn.esprit.mfb.entity.TypeCredit;

import java.time.LocalDate;

public interface IDemandeCreditService {
    DemandeCredit add(Long existingCreditId, Long id_fund, Long Id_client, Long Id_garantor, float year, TypeCredit typeC, float amount, LocalDate demandDate);
    Amortissement Simulateur(DemandeCredit credit);
    Amortissement[] TabAmortissement(DemandeCredit cr);
    float Calcul_mensualite(DemandeCredit cr);
    int CaculateLateDays(DemandeCredit  cr);
    DemandeCredit retrieveCredit(Long idCredit);

    DemandeCredit updateCredit(Long existingCreditId, Long id_fund, Long Id_client, Long Id_garantor, float year, TypeCredit typeC, float amount, LocalDate demandDate);


     void DeleteCredit(Long id);
}
