package io.camp.campsite.repository;

import io.camp.campsite.model.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season,Long> , SeasonRepositoryCustom {
}
