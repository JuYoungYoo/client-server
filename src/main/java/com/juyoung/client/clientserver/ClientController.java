package com.juyoung.client.clientserver;

import com.juyoung.client.clientserver.domain.Boards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    private String apiURL = "http://localhost:8080/api/boards";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/boards")
    public ResponseEntity<List> index(Model model) {
        String url = apiURL;  // http://localhost:8080/api/boards
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        logger.info(":::response.getBody : " + response.getBody());
        return response;
    }

    @GetMapping("/boards/{seq}")
    public ResponseEntity<HashMap> show(@PathVariable("seq") int seq, Model model) {
        String url = apiURL + "/" + seq;    //http://localhost:port/api/boards/1
        ResponseEntity<HashMap> response = restTemplate.getForEntity(url, HashMap.class);
        logger.info(":::response.getBody : " + response.getBody().toString());
        return response;
    }

    @PostMapping("/boards/add")
    public ResponseEntity<HashMap> create(@RequestBody Boards board) {
        String url = apiURL; // http://localhost:8080/api/boards
        ResponseEntity<HashMap> response = restTemplate.postForEntity(url, board, HashMap.class);
        logger.info(":::response.getBody : " + response.getBody().toString());
        return response;
    }

    @PostMapping("/boards/update")
    public ResponseEntity<Boards> update(@RequestBody Boards board) {
        long id = board.getSeq();
        String url = apiURL + "/" + id; // http://localhost:port/api/boards/1

        // 방법 1. put
        // restTemplate.put(url, board, HashMap.class);

        // 방법 2. exchange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Boards> entity = new HttpEntity(board, headers);

        ResponseEntity<Boards> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Boards.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @PostMapping("/boards/delete")
    public ResponseEntity delete(@RequestBody int seq) {
        URI uri = URI.create(apiURL + "/" + seq);
        restTemplate.delete(uri);   // return X
        return new ResponseEntity(HttpStatus.OK);
    }
}
