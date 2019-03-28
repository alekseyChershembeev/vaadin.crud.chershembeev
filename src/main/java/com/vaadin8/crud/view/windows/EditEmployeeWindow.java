package com.vaadin8.crud.view.windows;

import com.AisaTest06.check.fields.CheckMail;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Employee;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsEmployee;
import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class EditEmployeeWindow extends Window {

    private static Logger logger = Logger.getLogger(EditEmployeeWindow.class.getName());



    public EditEmployeeWindow(Employee employee) {
        setStyleName("Редактировать сотрудника");
        VerticalLayout editVerticalLayout = new VerticalLayout();

        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setContent(editVerticalLayout);
        setWidth(400f, Unit.PIXELS);
        setHeight(350f, Unit.PIXELS);
        setResizeLazy(false);

        FieldsEmployee fieldsEmployee = new FieldsEmployee();
        TextField fullName = fieldsEmployee.getFullName();
        DateField dateField = fieldsEmployee.getDateField();
        TextField email = fieldsEmployee.getEmailTextField();

        EmployeeDao employeeDao = new EmployeeDaoImpl();

        final String[] fullNameArr = {employee.getFullName()};
        final String[] dateArr = {employee.getBirthDate()};
        final String[] emailArr = {employee.getEmail()};

        final String[] companyNameArr = {employee.getNameCompany()};

        Button editEmployee = new Button("Редактировать");
        editEmployee.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        editEmployee.setSizeFull();
        editEmployee.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        editEmployee.setIcon(VaadinIcons.EDIT);

        Button cancel = new Button("Отменить", clickEvent -> close());

        cancel.setSizeFull();

        fullName.setRequiredIndicatorVisible(true);
        dateField.setRequiredIndicatorVisible(true);
        email.setRequiredIndicatorVisible(true);

        fullName.setValue(fullNameArr[0]);
        dateField.setValue(LocalDate.parse(dateArr[0]));
        email.setValue(emailArr[0]);


        editVerticalLayout.addComponents(fullName, dateField,
                email, editEmployee, cancel);
         /*
        Заполняем константу даты рождения сотрудника из поля
        */

        dateField.addValueChangeListener(
                (HasValue.ValueChangeListener<LocalDate>) valueChangeEvent -> {
                    LocalDate date = valueChangeEvent.getValue();
                    try {
                        dateArr[0] = date.toString();
                    }catch (NullPointerException ex){
                        logger.warning("NPE не заполнена дата рождения " + ex);
                    }

                });

        /*
        Заполняем константу email сотрудника из поля
        */

        email.addValueChangeListener(
                (HasValue.ValueChangeListener<String>) valueChangeEvent ->
                        emailArr[0] = valueChangeEvent.getValue());
        /*
        Заполняем константу имени сотрудника из поля
        */

        fullName.addValueChangeListener(
                (HasValue.ValueChangeListener<String>) valueChangeEvent ->
                        fullNameArr[0] = valueChangeEvent.getValue());



        /*
         Редактируем сотрудника, если заполнены все поля
        */

        editEmployee.addClickListener((Button.ClickListener) clickEvent13 -> {

            try {

                employee.setNameCompany(fullNameArr[0]);
                employee.setBirthDate(dateArr[0]);
                employee.setEmail(emailArr[0]);
                employee.setFullName(fullNameArr[0]);


                if (!((fullNameArr[0].isEmpty() || dateArr[0].isEmpty()
                        || !CheckMail.isValidPhone(emailArr[0]) || companyNameArr[0].isEmpty()))) {

                    employeeDao.editEmployee(employee);

                    MainLayout.employeeGrid.setItems(employeeDao.selectAllEmployees());

                    close();

                } else {
                    fieldsEmployee.check(fullName);
                    fieldsEmployee.check(dateField);
                    fieldsEmployee.check(email);
                    logger.warning("Неверная редакция сотрудника " + employee);
                }

            } catch (NullPointerException ex) {
                logger.warning("NPE сотрудника " + employee + " " + ex)
                ;
            }
        });

    }


}
