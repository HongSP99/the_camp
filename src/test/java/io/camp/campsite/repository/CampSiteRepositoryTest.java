package io.camp.campsite.repository;

import io.camp.config.QuerydslConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yaml")
@Import(QuerydslConfiguration.class)
public class CampSiteRepositoryTest {

    @Autowired
    private CampSiteRepository campSiteRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private SiteRepository siteRepository;


    @BeforeAll
    public  void setUp(){


    }

    @Test
    public void test(){

    }
}