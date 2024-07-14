package nl.hkstwk.spring6reactive.mappers;

import nl.hkstwk.spring6reactive.domain.Beer;
import nl.hkstwk.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    BeerDTO beerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDTO beerDTO);
}
