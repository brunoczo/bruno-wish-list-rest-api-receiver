package br.com.brunooliveira.jaya.wishlistrestapi.receiver.repositories;

import br.com.brunooliveira.jaya.wishlistrestapi.receiver.dto.WishListDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface WishListReposirotiry extends ReactiveMongoRepository<WishListDTO, String>{
}
