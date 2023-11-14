package br.com.brunooliveira.jaya.wishlistrestapi.receiver.service;


import br.com.brunooliveira.jaya.wishlistrestapi.receiver.dto.WishListDTO;
import br.com.brunooliveira.jaya.wishlistrestapi.receiver.repositories.WishListReposirotiry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.rabbitmq.Receiver;

import javax.annotation.PostConstruct;

@Service
public class ReceiverMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverMessage.class);
    //@Value("${wishlist.queue}")
    private static  String QUEUE = "wishListqueue";


    @Autowired
    WishListReposirotiry wishListReposirotiryy;
    @Autowired
    Receiver receiver;
    @PostConstruct
    private void init()  {
        consume();
    }

    public Disposable consume() {

        return receiver.consumeAutoAck(QUEUE).subscribe(m -> {
            //1. Deserialize byte to json
            String json = SerializationUtils.deserialize(m.getBody());
            ObjectMapper mapper = new ObjectMapper();
            WishListDTO wl;
            //2. map json to Order object
            LOGGER.info(json.toString());
            try {
                wl = mapper.readValue(json, WishListDTO.class);
                wishListReposirotiryy.save(wl).block();
                LOGGER.info("Salvo com sucesso");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

}
