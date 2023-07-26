package com.gmail.sge.serejka.happyurl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URLRecord, Long> {
    URLRecord findByUrl(String url);
    URLRecord deleteById(long id);
}
