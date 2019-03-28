package com.vaadin8.crud.view.windows;


import com.AisaTest06.check.fields.CheckMail;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.entity.Employee;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsEmployee;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class AddEmployeeWindow extends Window {


    private static Logger logger = Logger.getLogger(AddEmployeeWindow.class.getName());

    public AddEmployeeWindow() {

        setWidth(420f, Unit.PIXELS);

        FormLayout content = new FormLayout();

        content.setMargin(true);

        setModal(true);

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        CompanyDao companyDao = new CompanyDaoImpl();
        center();
        setClosable(true);
        setDraggable(false);
        setResizeLazy(false);

        setStyleName("Добавить нового сотрудника");

        List<Company> companyList = companyDao.selectAllCompanies();

        ComboBox<Company> selectAllCompanies = new ComboBox<>("Выбрать компанию");
        selectAllCompanies.setItems(companyList);
        selectAllCompanies.setItemCaptionGenerator(Company::getName);
        selectAllCompanies.setEmptySelectionAllowed(false);
        selectAllCompanies.setWidth(78,Unit.PERCENTAGE);

        Button addEmployee = new com.vaadin.ui.Button("Добавить");
        addEmployee.setSizeFull();
        addEmployee.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        addEmployee.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addEmployee.setIcon(VaadinIcons.INSERT);


        Button cancel = new Button("Отменить", clickEvent -> close());

        cancel.setSizeFull();

        FieldsEmployee fieldsEmployee = new FieldsEmployee();

        TextField fullNameTextField = fieldsEmployee.getFullName();
        DateField dateField = fieldsEmployee.getDateField();
        TextField emailField = fieldsEmployee.getEmailTextField();

        final String[] fullNameArr = {""};
        final String[] dateFieldArr = {""};
        final String[] emailArr = {""};
        final String[] companyNameArr = {""};
        final int[] companyId = {0};

        /*
        Заполяем данные Id Компании и Именя компании констант из комбобокса
        */
        selectAllCompanies.addValueChangeListener(valueChangeEvent -> {
            companyId[0] = valueChangeEvent.getValue().getCompanyId();
            companyNameArr[0] = valueChangeEvent.getValue().getName();
            fieldsEmployee.check(selectAllCompanies);
        });

        content.addComponents(selectAllCompanies, fullNameTextField, dateField, emailField, addEmployee, cancel);

        setContent(content);

        fullNameTextField.setRequiredIndicatorVisible(true);
        dateField.setRequiredIndicatorVisible(true);
        emailField.setRequiredIndicatorVisible(true);

        /*
        Заполняем константу имени сотрудника из поля
        */
        fullNameTextField.addValueChangeListener(valueChangeEvent ->
                fullNameArr[0] = valueChangeEvent.getValue()
        );
        /*
        Заполняем константу даты рождения сотрудника из поля
        */
        dateField.addValueChangeListener(valueChangeEvent ->
                dateFieldArr[0] = String.valueOf(valueChangeEvent.getValue()));
        /*
        Заполняем константу email сотрудника из поля
        */
        emailField.addValueChangeListener(valueChangeEvent ->
                emailArr[0] = valueChangeEvent.getValue());

        /*
         Добавляем сотрудника, если заполнены все поля
        */
        addEmployee.addClickListener((Button.ClickListener) clickEvent6 -> {

            if (!((fullNameArr[0].isEmpty() || dateFieldArr[0].isEmpty()
                    || !CheckMail.isValidPhone(emailArr[0]) || companyNameArr[0].isEmpty()))) {


                Employee employee;
                try {

                    employee = new Employee(fullNameArr[0], dateFieldArr[0], emailArr[0], companyId[0], companyNameArr[0]);


                    if (!(fullNameArr[0].isEmpty() || dateFieldArr[0].isEmpty() ||
                            emailArr[0].isEmpty())) {

                        employeeDao.insertEmployee(employee);

                        MainLayout.employeeGrid.setItems(employeeDao.selectAllEmployees());

                        close();

                    }
                } catch (NumberFormatException ex) {
                    logger.warning("Неверные данные компании " + ex);
                } catch (NullPointerException ex) {
                    logger.warning("NPE " + ex);
                }
            } else {
                fieldsEmployee.check(fullNameTextField);
                fieldsEmployee.check(dateField);
                fieldsEmployee.check(emailField);
                fieldsEmployee.check(selectAllCompanies);

                logger.warning("Необходимо заполнить все данные сотрудника "
                        + fullNameArr[0] + " " + dateFieldArr[0] + " " + emailArr[0] + " " + companyId[0]);
            }
        });

    }
}



