package com.egar.exercise.repos;

import com.egar.exercise.persistance.SharePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharePriceRepo extends JpaRepository<SharePrice, Long> {

    SharePrice getFirstById(Long id);
    List<SharePrice> findAllByCompanyId(Long id);
}
