package io.camp.campsite.repository;

import io.camp.campsite.model.entity.Campsite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampSiteRepository extends JpaRepository<Campsite,Long> ,CampSiteRepositoryCustom{
}
