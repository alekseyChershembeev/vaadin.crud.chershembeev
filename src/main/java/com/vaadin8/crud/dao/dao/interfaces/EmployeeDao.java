package com.vaadin8.crud.dao.dao.interfaces;


import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.entity.Employee;

import java.util.List;


public interface EmployeeDao {
    /**
    Метод для
    добавления сотрудника 
     **/
    void insertEmployee(Employee employee);
    /**
    Метод для
    редактирования сотрудника
     **/
    void editEmployee(Employee employee);
    /**
    Метод для
    удаления сотрудника 
     **/
    void deleteEmployee(int employeeid);
    /**
    Метод для
    выбора всех сотрудников 
     **/
    List<Employee> selectAllEmployees();
    /**
    Метод для
    выбора сотрудника по полю
     **/
    List<Employee> searchAllEmployees(String search);
    /**
    Метод для
    добавления сотрудника
     **/
    void editEmployeeName(Company company);
}
