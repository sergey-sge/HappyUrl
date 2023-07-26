package com.gmail.sge.serejka.happyurl;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class URLService {
    private final URLRepository urlRepository;
    private static final long term = 80000; //Срок годности ссылки

    public URLService(URLRepository urlRepository){
        this.urlRepository = urlRepository;
    }
    @Transactional
    public long saveUrl(UrlDTO urlDTO){
        var urlRecord = urlRepository.findByUrl(urlDTO.getUrl());
        if (urlRecord == null){
            urlRecord = URLRecord.of(urlDTO);
            urlRepository.save(urlRecord);
        }
        return urlRecord.getId();
    }

    @Transactional
    public String getUrl(long id){
        var urlOpt = urlRepository.findById(id);
        if (urlOpt.isEmpty()){
            return null;
        }

        var urlRecord = urlOpt.get();
        urlRecord.setCount(urlRecord.getCount() + 1);
        urlRecord.setLastAccess(new Date());

        return urlRecord.getUrl();
    }
    @Transactional(readOnly = true)
    public List<URLStatDTO> getStatistics(){
        var records = urlRepository.findAll();
        var result = new ArrayList<URLStatDTO>();

        records.forEach(x -> result.add(x.toStatDTO()));

        return result;
    }

    @Transactional
    public void delete(long id){
        urlRepository.deleteById(id);
    }

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void deleteOldLinks(){
        List<URLRecord> allLinks = urlRepository.findAll();
        for (URLRecord url : allLinks){
            if (!checkIfActive(url)){
                urlRepository.delete(url);
            }
        }
    }

    public long getIdByURL(String url){
        return urlRepository.findByUrl(url).getId();
    }


    private static boolean checkIfActive(URLRecord urlRecord){
        long activeDate = new Date().getTime();
        long lastAccess = urlRecord.getLastAccess().getTime();
        if (activeDate - lastAccess > term){
            return false;
        }
        return true;
    }
}