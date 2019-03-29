package com.vaadin8.crud.dao.dao.interfaces;

import com.vaadin8.crud.entity.Company;


import java.util.List;


public interface CompanyDao {
    /**
    Метод для
    добавления компании
    **/
    void insertCompany(Company company);
    /**
    Метод для
    редактирования компании
    **/
    void editCompany(Company company);
    /**
    Метод для
    удаления компании
    **/
    void deleteCompany(int companyid);
    /**
    Метод для
    выбора всех компаний
    **/
    List selectAllCompanies();
    /**
    Метод для
    поиска компании по полю 
    **/
    List<Company> searchAllCompanies(String search);
    /**
    Метод для
    проверки компании по имени компании
    **/
    boolean checkCompanyByName(String name);
}
