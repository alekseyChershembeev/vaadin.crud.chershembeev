package com.vaadin8.crud.view.windows;

import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Employee;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class DeleteEmployeeWindow extends Window {

    private static Logger logger = Logger.getLogger(DeleteEmployeeWindow.class.getName());


    //    List<Company> companyList;
    //    ArrayList<Company> listCompany;
    //    Set<Company> companySet;

    private Set<Employee> employeeSet;
    private List<Employee> employeeList;
    private ArrayList<Employee> employeeArrayList;

    public DeleteEmployeeWindow() {
        setStyleName("Удалить сотрудника");

        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setWidth(270f, Unit.PIXELS);
        setResizeLazy(false);

        EmployeeDao employeeDao = new EmployeeDaoImpl();

        Button deleteEmployee = new Button("Удалить");
        deleteEmployee.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        deleteEmployee.setSizeFull();
        deleteEmployee.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteEmployee.setIcon(VaadinIcons.MINUS);

        Button cancelButton = new Button("Отменить", clickEvent -> close());

        cancelButton.setSizeFull();

        CheckBoxGroup<Employee> selectAllEmployees = new CheckBoxGroup<>("Выбрать сотрудника");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(selectAllEmployees, deleteEmployee, cancelButton);
        setContent(verticalLayout);

        employeeList = employeeDao.selectAllEmployees();

        selectAllEmployees.setItems(employeeList);
        selectAllEmployees.setItemCaptionGenerator(Employee::getFullName);

        selectAllEmployees.addValueChangeListener(valueChangeEvent -> {

           employeeSet = valueChangeEvent.getValue();

            logger.info("Выраны сотрудники " + employeeSet);

             employeeArrayList = new ArrayList<>(employeeSet);

            /*
             Удаляем выбранных сотрудников из комбобокса по ID сотрудника
            */



        });

        deleteEmployee.addClickListener(clickEvent -> {

            try {
                if (employeeSet.size()>0) {
                    UserConfirmationEmployee userConfirmationEmployee =
                            new UserConfirmationEmployee(employeeSet,employeeArrayList);
                    UI.getCurrent().addWindow(userConfirmationEmployee);
                    close();
                }
                else {
                    logger.warning("Не выбраны сотрудники в чекбоксе ");
                }
            }catch (NullPointerException ex){
                logger.warning(" NPE deleteEmployee "+ ex);
            }
        });

    }
}
