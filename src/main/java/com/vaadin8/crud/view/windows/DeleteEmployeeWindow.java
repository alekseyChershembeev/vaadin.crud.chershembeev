package com.vaadin8.crud.view.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class DeleteEmployeeWindow extends Window {

    private static Logger logger = Logger.getLogger(DeleteEmployeeWindow.class.getName());

    private Set<Employee> employeeSet;
    private List<Employee> employeeList;
    private ArrayList<Employee> employeeArrayList;
    private EmployeeDao employeeDao;
    private Button deleteEmployee;
    private Button cancelButton;
    private CheckBoxGroup<Employee> selectAllEmployees;

    public DeleteEmployeeWindow() {
        employeeDao = new EmployeeDaoImpl();
        employeeList = employeeDao.selectAllEmployees();

        setStyle();
        setComponents();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(selectAllEmployees, deleteEmployee, cancelButton);

        setContent(verticalLayout);

        setClickListeners();
    }

    private void setStyle() {
        setStyleName("Удалить сотрудника");
        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setWidth(270f, Unit.PIXELS);
        setResizeLazy(false);
    }

    private void setComponents() {


        deleteEmployee = new Button("Удалить");
        deleteEmployee.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        deleteEmployee.setSizeFull();
        deleteEmployee.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteEmployee.setIcon(VaadinIcons.MINUS);

        cancelButton = new Button("Отменить", clickEvent -> close());
        cancelButton.setSizeFull();

        selectAllEmployees = new CheckBoxGroup<>("Выбрать сотрудника");
        selectAllEmployees.setItems(employeeList);
        selectAllEmployees.setItemCaptionGenerator(Employee::getFullName);
    }

    private void setClickListeners() {
          /*
             Удаляем выбранных сотрудников из комбобокса по ID сотрудника
            */
        selectAllEmployees.addValueChangeListener(valueChangeEvent -> {
            employeeSet = valueChangeEvent.getValue();
            employeeArrayList = new ArrayList<>(employeeSet);

            logger.info("Выраны сотрудники " + employeeSet);
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
