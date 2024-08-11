package io.camp.user.repository;

import io.camp.user.model.User;
import io.camp.user.model.dto.UserReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<UserReservationDto> userGetReservations(User loginUser, Pageable pageable);
}
