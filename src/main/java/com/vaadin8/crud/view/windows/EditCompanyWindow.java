package com.vaadin8.crud.view.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin8.crud.check.fields.CheckCompanyEntity;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsCompany;

import java.util.logging.Logger;


public class EditCompanyWindow extends Window {

    private static Logger logger = Logger.getLogger(EditCompanyWindow.class.getName());

    private CompanyDao companyDao;
    private EmployeeDao employeeDao;
    private Button editCompany;
    private Button cancel;
    private FieldsCompany fieldsCompany;
    private TextField nameField;

    private TextField nipField;
    private TextField addressField;
    private TextField phoneField;

    public EditCompanyWindow(Company company) {

        setStyle();
        setComponents(company);


        companyDao = new CompanyDaoImpl();
        employeeDao = new EmployeeDaoImpl();

        VerticalLayout editWindowLayout = new VerticalLayout();

        editWindowLayout.addComponents(nameField, addressField, nipField, phoneField, editCompany, cancel);

        setContent(editWindowLayout);

        setClickListeners(company);

    }

    private void setStyle() {
        setStyleName("Редактировать компанию");
        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setWidth(400f, Unit.PIXELS);
        setHeight(420, Unit.PIXELS);
        setResizeLazy(false);
    }


    private void setComponents(Company company) {

        editCompany = new Button("Редактировать");
        editCompany.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        editCompany.setSizeFull();
        editCompany.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        editCompany.setIcon(VaadinIcons.EDIT);
        cancel = new Button("Отменить", clickEvent -> close());
        cancel.setSizeFull();

        fieldsCompany = new FieldsCompany();
        nameField = fieldsCompany.getFullNameTextField();
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        nameField.setSizeFull();
        nameField.setRequiredIndicatorVisible(true);
        nameField.setValue(company.getName());
        nipField = fieldsCompany.getNipTextField();
        nipField.setRequiredIndicatorVisible(true);
        nipField.setValue(String.valueOf(company.getNip()));
        addressField = fieldsCompany.getAddressTextField();
        addressField.setRequiredIndicatorVisible(true);
        addressField.setValue(company.getAddress());
        phoneField = fieldsCompany.getPhoneTextField();
        phoneField.setRequiredIndicatorVisible(true);
        phoneField.setValue(String.valueOf(company.getPhone()));

    }
    private void setClickListeners(Company company) {
        /*
        Заполняем перменные из поля Имя компании
        */
        nameField.addValueChangeListener(valueChangeEvent ->
                company.setName(valueChangeEvent.getValue())
        );
         /*
        Заполняем пеменные из поля ИНН компании
        */
        nipField.addValueChangeListener(valueChangeEvent ->
                company.setNip(valueChangeEvent.getValue()));
        /*
        Заполняем пеменные из поля Адрес компании
        */
        addressField.addValueChangeListener(valueChangeEvent ->
                company.setAddress(valueChangeEvent.getValue()));
          /*
        Заполняем пеменные из поля Телефон компании
        */
        phoneField.addValueChangeListener(valueChangeEvent ->
                company.setPhone(valueChangeEvent.getValue()));
         /*
         Редактируем компанию, если такой нет в БД и если заполнены правильно все поля
         Редактируем у сотрудника название компании при изменении компании
        */
        editCompany.addClickListener((Button.ClickListener) clickEvent15 -> {
            try {
                if (CheckCompanyEntity.checkCompany(company)) {

                    companyDao.editCompany(company);
                    employeeDao.editEmployeeName(company);
                    MainLayout.companyGrid.setItems(companyDao.selectAllCompanies());
                    close();

                } else {
                    fieldsCompany.check(nameField);
                    fieldsCompany.check(nipField);
                    fieldsCompany.check(addressField);
                    fieldsCompany.check(phoneField);

                    logger.warning("Неверные данные компании " + company);
                }
            } catch (NullPointerException ex) {

                logger.warning("NPE компании " + company + " " + ex);
            }
        });

    }
}

