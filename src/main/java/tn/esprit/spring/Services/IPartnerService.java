package tn.esprit.spring.Services;

import tn.esprit.spring.entity.Partner;

import java.util.List;
import java.util.Optional;

public interface IPartnerService {
    Partner saveOrUpdatePartner(Partner partner);
    List<Partner> getAllPartners();
    Optional<Partner> getPartnerById(Long partnerId);
    void deletePartnerById(Long partnerId);
}
