package com.br.CompaniesMS.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.CompaniesMS.model.Company;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, UUID> {}

