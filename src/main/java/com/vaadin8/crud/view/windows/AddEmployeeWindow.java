package com.vaadin8.crud.view.windows;


import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin8.crud.check.fields.CheckEmployeeEntity;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.entity.Employee;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsEmployee;

import java.util.List;
import java.util.logging.Logger;


public class AddEmployeeWindow extends Window {

    private static Logger logger = Logger.getLogger(AddEmployeeWindow.class.getName());

    private Employee employee;
    private EmployeeDao employeeDao;
    private CompanyDao companyDao;

    private ComboBox<Company> selectAllCompanies;
    private List companyList;
    private Button addEmployee;
    private Button cancel;

    private FieldsEmployee fieldsEmployee;
    private TextField fullNameTextField;
    private DateField dateField;
    private TextField emailField;

    public AddEmployeeWindow() {

        setStyle();

        employeeDao = new EmployeeDaoImpl();
        companyDao = new CompanyDaoImpl();

        companyList = companyDao.selectAllCompanies();
        setComponents();

        employee = new Employee();

        FormLayout content = new FormLayout();
        content.setMargin(true);
        content.addComponents(selectAllCompanies, fullNameTextField, dateField, emailField, addEmployee, cancel);
        setContent(content);

        setClickListeners();

    }

    private void setStyle() {
        setStyleName("Добавить нового сотрудника");
        setWidth(420f, Unit.PIXELS);
        setModal(true);
        center();
        setClosable(true);
        setDraggable(false);
        setResizeLazy(false);
    }

    private void setComponents() {

        selectAllCompanies = new ComboBox<>("Выбрать компанию");
        selectAllCompanies.setItems(companyList);
        selectAllCompanies.setItemCaptionGenerator(Company::getName);
        selectAllCompanies.setEmptySelectionAllowed(false);
        selectAllCompanies.setWidth(78, Unit.PERCENTAGE);

        addEmployee = new com.vaadin.ui.Button("Добавить");
        addEmployee.setSizeFull();
        addEmployee.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addEmployee.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addEmployee.setIcon(VaadinIcons.INSERT);

        cancel = new Button("Отменить", clickEvent -> close());
        cancel.setSizeFull();

        fieldsEmployee = new FieldsEmployee();

        fullNameTextField = fieldsEmployee.getFullName();
        fullNameTextField.setRequiredIndicatorVisible(true);

        dateField = fieldsEmployee.getDateField();
        dateField.setDateFormat("yyyy-MM-dd");
        dateField.setRequiredIndicatorVisible(true);

        emailField = fieldsEmployee.getEmailTextField();
        emailField.setRequiredIndicatorVisible(true);

    }

    private void setClickListeners() {

         /*
        Заполяем данные Id Компании и имя компании
        */
        selectAllCompanies.addValueChangeListener(valueChangeEvent -> {
            employee.setCompanyId(valueChangeEvent.getValue().getCompanyId());
            employee.setNameCompany(valueChangeEvent.getValue().getName());
            fieldsEmployee.check(selectAllCompanies);
        });

        /*
        Заполняем константу имени сотрудника из поля
        */
        fullNameTextField.addValueChangeListener(valueChangeEvent ->
                employee.setFullName(valueChangeEvent.getValue())

        );
        /*
        Заполняем константу даты рождения сотрудника из поля
        */
        dateField.addValueChangeListener(valueChangeEvent ->
                employee.setBirthDate(valueChangeEvent.getValue().toString()));
        /*
        Заполняем константу email сотрудника из поля
        */
        emailField.addValueChangeListener(valueChangeEvent ->
                employee.setEmail(valueChangeEvent.getValue()));

        /*
         Добавляем сотрудника, если заполнены все поля
        */

        addEmployee.addClickListener((Button.ClickListener) clickEvent6 -> {

            if (CheckEmployeeEntity.checkEmployee(employee)) {
                try {
                    employeeDao.insertEmployee(employee);
                    MainLayout.employeeGrid.setItems(employeeDao.selectAllEmployees());
                    close();

                } catch (NullPointerException ex) {
                    logger.warning("NPE " + ex);
                }
            } else {
                fieldsEmployee.check(fullNameTextField);
                fieldsEmployee.check(dateField);
                fieldsEmployee.check(emailField);
                fieldsEmployee.check(selectAllCompanies);

                logger.warning("Необходимо заполнить все данные сотрудника " + employee.toString());
            }
        });
    }


}



