package com.gmail.sge.serejka.happyurl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class URLController {
    private final URLService urlService;

    public URLController (URLService urlService){
        this.urlService = urlService;
    }
    @PostMapping("shorten")
    public URLResultDTO shorten(@RequestBody UrlDTO urlDTO){
        long id = urlService.saveUrl(urlDTO);

        var result = new URLResultDTO();
        result.setUrl(urlDTO.getUrl());
        result.setShortURL(Long.toString(id));

        return result;
    }

    @GetMapping("my/{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") long id){
        var url = urlService.getUrl(id);

        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        headers.setCacheControl("no-cache, no-store, must-revalidate");

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    @GetMapping("stat")
    public List<URLStatDTO> stat(){
        return urlService.getStatistics();
    }

    @PostMapping("delete")
    public String delete(@RequestBody UrlDTO urlDTO){
        long id = urlService.getIdByURL(urlDTO.getUrl());
        urlService.delete(id);
        return "URL was deleted";
    }
}
