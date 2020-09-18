package com.egar.exercise.repos;

import com.egar.exercise.persistance.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    List<Company> findAllByName(String name);
}
