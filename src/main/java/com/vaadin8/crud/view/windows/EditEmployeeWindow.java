package com.vaadin8.crud.view.windows;

import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin8.crud.check.fields.CheckEmployeeEntity;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Employee;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsEmployee;

import java.time.LocalDate;
import java.util.logging.Logger;


public class EditEmployeeWindow extends Window {

    private static Logger logger = Logger.getLogger(EditEmployeeWindow.class.getName());

    private EmployeeDao employeeDao;
    private FieldsEmployee fieldsEmployee;
    private TextField fullName;
    private DateField dateField;
    private TextField email;
    private Button editEmployee;
    private Button cancel;

    public EditEmployeeWindow(Employee employee) {
        setStyle();
        setComponents(employee);
        setClickListeners(employee);

        VerticalLayout editVerticalLayout = new VerticalLayout();
        setContent(editVerticalLayout);

        editVerticalLayout.addComponents(fullName, dateField,
                email, editEmployee, cancel);
    }
    private void setStyle() {
        setStyleName("Редактировать сотрудника");
        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setWidth(400f, Unit.PIXELS);
        setHeight(350f, Unit.PIXELS);
        setResizeLazy(false);
    }

    private void setComponents(Employee employee) {
        employeeDao = new EmployeeDaoImpl();

        fieldsEmployee = new FieldsEmployee();
        fullName = fieldsEmployee.getFullName();
        fullName.setRequiredIndicatorVisible(true);
        fullName.setValue(employee.getFullName());
        dateField = fieldsEmployee.getDateField();
        dateField.setRequiredIndicatorVisible(true);
        dateField.setValue(LocalDate.parse(employee.getBirthDate()));
        email = fieldsEmployee.getEmailTextField();
        email.setRequiredIndicatorVisible(true);
        email.setValue(employee.getEmail());

        editEmployee = new Button("Редактировать");
        editEmployee.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        editEmployee.setSizeFull();
        editEmployee.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        editEmployee.setIcon(VaadinIcons.EDIT);

        cancel = new Button("Отменить", clickEvent -> close());
        cancel.setSizeFull();
    }

    private void setClickListeners(Employee employee) {
           /*
        Заполняем константу даты рождения сотрудника из поля
        */
        dateField.addValueChangeListener(
                (HasValue.ValueChangeListener<LocalDate>) valueChangeEvent -> {
                    LocalDate date = valueChangeEvent.getValue();
                    try {
                        employee.setBirthDate(date.toString());
                    } catch (NullPointerException ex) {
                        logger.warning("NPE не заполнена дата рождения " + ex);
                    }

                });
        /*
        Заполняем константу email сотрудника из поля
        */

        email.addValueChangeListener(
                (HasValue.ValueChangeListener<String>) valueChangeEvent ->
                        employee.setEmail(valueChangeEvent.getValue()));
        /*
        Заполняем константу имени сотрудника из поля
        */

        fullName.addValueChangeListener(
                (HasValue.ValueChangeListener<String>) valueChangeEvent ->
                        employee.setFullName(valueChangeEvent.getValue()));
        /*
         Редактируем сотрудника, если заполнены все поля
        */

        editEmployee.addClickListener((Button.ClickListener) clickEvent13 -> {

            try {

                if (CheckEmployeeEntity.checkEmployee(employee)) {

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
