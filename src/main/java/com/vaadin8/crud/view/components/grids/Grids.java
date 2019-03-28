package com.vaadin8.crud.view.components.grids;

import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.entity.Employee;
import com.vaadin8.crud.view.windows.EditCompanyWindow;
import com.vaadin8.crud.view.windows.EditEmployeeWindow;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

import java.util.logging.Logger;

public class Grids extends Grid {

    private static Logger logger = Logger.getLogger(Grid.class.getName());

    /**
    Заполняем Grid столбцами сотрудника и
    по двойному нажатию вызываем окно редактирования
    **/

    private static  final Grid<Employee> employeeGrid = new Grid<>();
    private static final Grid<Company> companyGrid = new Grid<>();
    public static Grid<Employee> gridEmployees() {



        employeeGrid.addColumn(Employee::getFullName).setCaption("ФИО");
        employeeGrid.addColumn(Employee::getBirthDate).setCaption("Дата Рождения");
        employeeGrid.addColumn(Employee::getEmail).setCaption("Email");
        employeeGrid.addColumn(Employee::getNameCompany).setCaption("Имя компании");



        employeeGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick())
                UI.getCurrent().addWindow(new EditEmployeeWindow(event.getItem()));
            logger.info("Выбран сотрудник " + event.getItem());
        });

        return employeeGrid;
    }

     /**
    Заполняем Grid столбцами компании и
    по двойному нажатию вызываем окно редактирования
    **/


    public static Grid<Company> gridCompanies() {

        companyGrid.addColumn(Company::getName).setCaption("Имя Компании");
        companyGrid.addColumn(Company::getNip).setCaption("ИНН");
        companyGrid.addColumn(Company::getAddress).setCaption("Адрес");
        companyGrid.addColumn(Company::getPhone).setCaption("Телефон");

        companyGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().addWindow(new EditCompanyWindow(event.getItem()));
                logger.info("Выбрана компания " + event.getItem());
            }
        });


        return companyGrid;
    }


}
