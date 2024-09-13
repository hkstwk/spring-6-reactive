package nl.hkstwk.spring6reactive.repositories;

import nl.hkstwk.spring6reactive.config.DatabaseConfig;
import nl.hkstwk.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Disabled
    @Test
    void testSaveNewBeer() {
        beerRepository.save(getTestBeer()).subscribe(System.out::println);
    }

    public static Beer getTestBeer(){
        return Beer.builder()
                .beerName("Karmeliet")
                .beerStyle("TRIPLE")
                .upc("abcde")
                .price(BigDecimal.valueOf(13.75))
                .quantityOnHand(10)
                .build();
    }
}