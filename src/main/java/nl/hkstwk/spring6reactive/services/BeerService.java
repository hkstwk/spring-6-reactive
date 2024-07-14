package nl.hkstwk.spring6reactive.services;

import nl.hkstwk.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;

public interface BeerService {
    Flux<BeerDTO> listBeers();
}
