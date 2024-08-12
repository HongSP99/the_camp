package io.camp.inventory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.inventory.model.Inventory;
import io.camp.inventory.model.QInventory;
import io.camp.user.model.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryCustomImpl implements  InventoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Inventory> findInventoryByUserEmail(String email) {
        QUser user = QUser.user;
        QInventory inventory = QInventory.inventory;

       return queryFactory.selectFrom(inventory)
                .where(inventory.user.email.eq(email))
                .fetch();
    }
}
