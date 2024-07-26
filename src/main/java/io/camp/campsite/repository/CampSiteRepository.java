package io.camp.campsite.repository;

import io.camp.campsite.model.entity.CampSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampSiteRepository extends JpaRepository<CampSite,Long> {
}
