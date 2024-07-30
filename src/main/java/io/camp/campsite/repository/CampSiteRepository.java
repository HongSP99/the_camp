package io.camp.campsite.repository;

import io.camp.campsite.model.entity.Campsite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampSiteRepository extends JpaRepository<Campsite, Long> {
    Page<Campsite> findByFacltNmContainingIgnoreCase(String facltNm, Pageable pageable);
}
