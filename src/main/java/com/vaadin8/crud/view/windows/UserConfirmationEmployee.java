package com.vaadin8.crud.view.windows;


import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Employee;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

class UserConfirmationEmployee extends Window {


    private Label label;
    private Button yes;
    private Button close;
    private FormLayout formLayout;
    private boolean confirmDeleteEmployee;
    private EmployeeDao employeeDao;


    private static Logger logger = Logger.getLogger(UserConfirmationEmployee.class.getName());


    UserConfirmationEmployee(Set<Employee> employeeSet, List<Employee> listEmployee) {

        employeeDao = new EmployeeDaoImpl();

        setStyleUserConfirmation();

        setComponents();

        setContent(formLayout);

        yes.addClickListener(clickEvent -> {
            confirmDeleteEmployee = true;
            deleteListener(employeeSet, listEmployee);
            logger.info("Одобрено удаление ");
            close();

        });
        close.addClickListener(clickEvent -> {
            confirmDeleteEmployee = false;
            logger.info("Удаление отменено");
            close();
        });

    }

    /**
     * Инициализируем компоненты UserConfirmationEmployee и добавляем и properties
     **/
    private void setComponents() {
        label = new Label("Удалить сотрудника/сотрудников ?");
        label.setSizeFull();
        yes = new Button("Да");
        yes.setWidth(94, Unit.PERCENTAGE);
        yes.setStyleName(ValoTheme.BUTTON_DANGER);
        yes.setIcon(VaadinIcons.MINUS);
        close = new Button("Отменить");
        close.setWidth(94, Unit.PERCENTAGE);
        formLayout = new FormLayout();
        formLayout.setMargin(true);
        formLayout.addComponents(label, yes, close);
    }


    private void setStyleUserConfirmation() {
        setStyleName("Удалить сотрудника/сотрудников ?");
        center();
        setClosable(true);
        setResizable(false);
        setModal(true);
        setDraggable(false);
        setWidth(340, Unit.PIXELS);
        setResizeLazy(false);
    }

    /**
     * Удаляем выбранных сотрудников из комбобокса по ID компании
     **/
    private void deleteListener(Set<Employee> employeeSet, List<Employee> listEmployee) {

        for (int i = 0; i < employeeSet.size(); i++) {
            if ((!listEmployee.isEmpty()) || confirmDeleteEmployee) {

                employeeDao.deleteEmployee(listEmployee.get(i).getEmployeeId());
                MainLayout.employeeGrid.setItems(employeeDao.selectAllEmployees());

                logger.info("Сотруник успешно удален " + listEmployee.get(i));
            } else {
                logger.warning("Невозможно удалить сотрудника");
            }
        }

    }


}
