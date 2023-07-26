package com.gmail.sge.serejka.happyurl;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class URLRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private long count;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccess;

    public URLRecord(){
        count = 0L;
        lastAccess = new Date();
    }

    private URLRecord(String url){
        this();
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public static URLRecord of(UrlDTO urlDTO){
        return new URLRecord(urlDTO.getUrl());
    }

    public URLStatDTO toStatDTO(){
        var result = new URLStatDTO();

        result.setUrl(url);
        result.setShortURL(Long.toString(id));
        result.setRedirects(count);
        result.setLastAccess(lastAccess);

        return result;
    }
}
