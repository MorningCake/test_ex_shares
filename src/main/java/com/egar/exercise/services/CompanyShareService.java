package com.egar.exercise.services;

import com.egar.exercise.dto.mappers.TableStringReadUpdateMapper;
import com.egar.exercise.dto.pojo.GraphDataDTO;
import com.egar.exercise.dto.pojo.GraphPointDTO;
import com.egar.exercise.dto.pojo.TableStringCreateDTO;
import com.egar.exercise.dto.pojo.TableStringReadUpdateDTO;
import com.egar.exercise.persistance.Company;
import com.egar.exercise.persistance.SharePrice;
import com.egar.exercise.repos.CompanyRepo;
import com.egar.exercise.repos.SharePriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyShareService {

    private CompanyRepo companyRepo;
    private SharePriceRepo sharePriceRepo;
    private TableStringReadUpdateMapper tableStringReadUpdateMapper;

    @Autowired
    public void setCompanyRepo(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }
    @Autowired
    public void setSharePriceRepo(SharePriceRepo sharePriceRepo) {
        this.sharePriceRepo = sharePriceRepo;
    }
    @Autowired
    public void setTableStringReadUpdateMapper(TableStringReadUpdateMapper tableStringReadUpdateMapper) {
        this.tableStringReadUpdateMapper = tableStringReadUpdateMapper;
    }

    public List<TableStringReadUpdateDTO> initializeTableByAllData () {
        List<SharePrice> priceTable = sharePriceRepo.findAll(Sort.by("company.name", "date"));
        return tableStringReadUpdateMapper.toDtoList(priceTable);
    }

    @Transient
    public void createNewStringIntoTable(TableStringCreateDTO dto) {
        Company company = this.getCompanyByNameOrCreateNewCompany(dto.getCompanyName());
        sharePriceRepo.save(new SharePrice(
                dto.getPriceDate(),
                dto.getSharePrice(),
                company
        ));
    }

    @Transient
    public void updateStringIntoTable(TableStringReadUpdateDTO dto) {
        Company company = this.getCompanyByNameOrCreateNewCompany(dto.getCompanyName());
        SharePrice priceFromDb = sharePriceRepo.getFirstById(dto.getPriceId());
        //сохранить компанию, чтобы проверить, есть ли у нее еще данные по ценам БД
        Company prevCompany = priceFromDb.getCompany();

        priceFromDb.setCompany(company);
        priceFromDb.setDate(dto.getPriceDate());
        priceFromDb.setPrice(dto.getSharePrice());
        sharePriceRepo.save(priceFromDb);

        // если изменена компания, проверить наличие данные по ценам предыдущей компании
        if (prevCompany.getId().compareTo(company.getId()) != 0) {
            List<SharePrice> prevCompanyData = sharePriceRepo.findAllByCompanyId(prevCompany.getId());
            if (prevCompanyData.size() == 0) {
                //если данных нет - удалить эту компанию
                companyRepo.delete(prevCompany);
            }
        }
    }

    public List<GraphDataDTO> getGraphPoints() {
        List<Company> companies = companyRepo.findAll();
        return companies.stream().map(com -> {
            List<SharePrice> prices = sharePriceRepo.findAllByCompanyId(com.getId());
            List<GraphPointDTO> points = prices.stream().map(p -> new GraphPointDTO(
                    p.getDate(),
                    p.getPrice()
            )).collect(Collectors.toList());
            return new GraphDataDTO(
                    com.getName(),
                    points);
        }).collect(Collectors.toList());
    }

    private Company getCompanyFromDbByName (String name) {
        List<Company> companyAsList = companyRepo.findAllByName(name);
        if (companyAsList.size() == 0) {
            return null;
        } else {
            return companyAsList.get(0);
        }
    }
    private Company getCompanyByNameOrCreateNewCompany(String name) {
        Company company = this.getCompanyFromDbByName(name);
        if (company == null) {
            company = new Company(name);
            return companyRepo.save(company);
        } else {
            return company;
        }
    }
}
