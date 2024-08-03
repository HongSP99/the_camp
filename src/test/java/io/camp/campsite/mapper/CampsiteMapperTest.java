package io.camp.campsite.mapper;

import io.camp.campsite.model.dto.CampSiteDto;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class CampsiteMapperTest {

    @Autowired
    CampsiteMapper campsiteMapper;

    @Autowired
   private CampSiteRepository campSiteRepository;



    @Test
    public void Campsiet_엔티티를_dto로_변환(){
        Campsite campsite = campSiteRepository.findById(1L).get();
        CampSiteDto campsiteDto = campsiteMapper.toCampsiteDto(campsite);
        assertNotEquals(campsiteDto,null);
    }

}