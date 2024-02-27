package com.br.CompaniesMS.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.br.CompaniesMS.model.Company;
import com.br.CompaniesMS.repository.CompaniesRepository;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    private CompaniesRepository companiesRepository;


    @GetMapping
    public List<Company> listCompanies(){
        return companiesRepository.findAll();
    }

    @PostMapping
    public Company saveCompany(@RequestBody Company company){
        UUID uuid = UUID.randomUUID();
        company.setId(uuid);
        return companiesRepository.save(company);
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable String id) throws Exception{
        UUID uuid = UUID.fromString(id);
        Company company = companiesRepository.findById(uuid).orElse(null);
        if(company != null){
            return company;
        }else {
            throw new Exception("ID da empresa inválido: " + id);

        }   
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company newData) throws Exception {
        try {
            UUID uuid = UUID.fromString(id);
            return companiesRepository.findById(uuid)
                .map(company -> {
                    company.setName(newData.getName());
                    company.setCnpj(newData.getCnpj());
                    return companiesRepository.save(company);

            }).orElse(null);
                
        } catch (Exception e) {
            throw new Exception("ID da empresa inválido: " + id);
        }
        
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        companiesRepository.deleteById(uuid);
    }
}
