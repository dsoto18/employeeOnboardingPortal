package com.cooksys.groupfinal.repositories;

import com.cooksys.groupfinal.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Override
    Optional<Company> findById(Long id);


}