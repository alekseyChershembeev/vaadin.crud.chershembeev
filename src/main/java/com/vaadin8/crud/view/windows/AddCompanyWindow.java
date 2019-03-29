package com.vaadin8.crud.view.windows;


import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin8.crud.check.fields.CheckCompanyEntity;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsCompany;

import java.util.logging.Logger;



public class AddCompanyWindow extends Window {

    private static Logger logger = Logger.getLogger(AddCompanyWindow.class.getName());

    private CompanyDao companyDao;
    private Button addCompany;
    private Button cancel;
    private FieldsCompany fieldsCompany;
    private TextField nameTextField;
    private TextField nipTextField;
    private TextField address;
    private TextField phone;
    private Company company;

    public AddCompanyWindow() {

        company = new Company();

        setComponents();
        setStyle();

        FormLayout content = new FormLayout();
        content.addComponents(nameTextField, nipTextField, address, phone, addCompany, cancel);
        content.setMargin(true);

        setContent(content);

        setClickListeners();
    }

    private void setStyle() {
        setStyleName("Добавить новую компанию");
        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setResizeLazy(false);
        setWidth(420f, Unit.PIXELS);
        setHeight(300f, Unit.PIXELS);
    }

    private void setComponents() {
        companyDao = new CompanyDaoImpl();

        addCompany = new Button("Добавить");
        addCompany.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addCompany.setIcon(VaadinIcons.INSERT);
        addCompany.setSizeFull();
        addCompany.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel = new Button("Отменить");
        cancel.setSizeFull();

        fieldsCompany = new FieldsCompany();
        nameTextField = fieldsCompany.getFullNameTextField();
        nameTextField.setRequiredIndicatorVisible(true);
        nipTextField = fieldsCompany.getNipTextField();
        nipTextField.setRequiredIndicatorVisible(true);
        address = fieldsCompany.getAddressTextField();
        address.setRequiredIndicatorVisible(true);
        phone = fieldsCompany.getPhoneTextField();
        phone.setRequiredIndicatorVisible(true);

    }

        /*
         Добавляем компанию, если такой нет в БД и если правильно заполнены все поля
        */

    private void setClickListeners(){
         /*
        Заполняем пеменные из поля Имя компании
        */
        nameTextField.addValueChangeListener(valueChangeEvent ->
                company.setName(valueChangeEvent.getValue())
        );
         /*
        Заполняем пеменные из поля ИНН компании
        */
        nipTextField.addValueChangeListener(valueChangeEvent ->
                company.setNip(valueChangeEvent.getValue()));
        /*
        Заполняем пеменные из поля Адрес компании
        */
        address.addValueChangeListener(valueChangeEvent ->
                company.setAddress(valueChangeEvent.getValue()));
         /*
        Заполняем пеменные из поля Телефон компании
        */
        phone.addValueChangeListener(valueChangeEvent ->
                company.setPhone(valueChangeEvent.getValue()));

        addCompany.addClickListener((Button.ClickListener) clickEvent6 -> {


            if (CheckCompanyEntity.checkCompany(company)) {
                try {
                    if
                    (companyDao.checkCompanyByName(company.getName())) {
                        logger.warning("Такая компания уже существет " + company.getName());
                        company.setName("");

                    } else {
                        companyDao.insertCompany(company);
                        MainLayout.companyGrid.setItems(companyDao.selectAllCompanies());
                        close();

                    }
                } catch (NullPointerException ex) {
                    logger.warning("NPE " + ex);

                }

            } else {
                fieldsCompany.check(nameTextField);
                fieldsCompany.check(nipTextField);
                fieldsCompany.check(address);
                fieldsCompany.check(phone);
                logger.warning("Необходимо заполнить все данные компании " + company.toString());

            }

        });

        cancel.addClickListener(clickEvent -> close());
    }

}



















