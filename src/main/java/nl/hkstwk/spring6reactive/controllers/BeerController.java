package nl.hkstwk.spring6reactive.controllers;

import lombok.RequiredArgsConstructor;
import nl.hkstwk.spring6reactive.model.BeerDTO;
import nl.hkstwk.spring6reactive.services.BeerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }
}
