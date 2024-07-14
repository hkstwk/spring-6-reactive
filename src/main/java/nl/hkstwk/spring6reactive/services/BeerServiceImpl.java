package nl.hkstwk.spring6reactive.services;

import lombok.RequiredArgsConstructor;
import nl.hkstwk.spring6reactive.mappers.BeerMapper;
import nl.hkstwk.spring6reactive.model.BeerDTO;
import nl.hkstwk.spring6reactive.repositories.BeerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDto);
    }
}
