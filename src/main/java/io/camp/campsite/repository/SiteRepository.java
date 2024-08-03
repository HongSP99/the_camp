package io.camp.campsite.repository;

import io.camp.campsite.model.entity.Site;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site,Long> , SiteRepositoryCustom {
    List<Site> findByZoneSeq(Long zoneSeq);
}
