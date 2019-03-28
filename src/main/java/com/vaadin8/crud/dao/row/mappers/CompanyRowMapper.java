package com.vaadin8.crud.dao.row.mappers;

import com.vaadin8.crud.entity.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper {

    /**
    Сопоставляем данные каждой строки компании
    **/
    /*
    Можно использовать вместо  new BeanPropertyRowMapper(Company.class);
    */
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Company company = new Company();
        company.setCompanyId(rs.getInt("companyid"));
        company.setName(rs.getString("name"));
        company.setNip(rs.getString("nip"));
        company.setAddress(rs.getString("address"));
        company.setPhone(rs.getString("phone"));
        return company;
    }
}
