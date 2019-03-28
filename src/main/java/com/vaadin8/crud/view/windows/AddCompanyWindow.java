package com.vaadin8.crud.view.windows;


import com.AisaTest06.check.fields.CheckNIP;
import com.AisaTest06.check.fields.CheckPhone;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsCompany;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.logging.Logger;


@SuppressWarnings("ALL")
public class AddCompanyWindow extends Window {

    private static Logger logger = Logger.getLogger(AddCompanyWindow.class.getName());


    public AddCompanyWindow() {

        Button addCompany = new Button("Добавить");
        addCompany.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        setWidth(420f, Unit.PIXELS);
        setHeight(300f, Unit.PIXELS);
        FormLayout content = new FormLayout();

        content.setMargin(true);

        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setResizeLazy(false);


        CompanyDao companyDao = new CompanyDaoImpl();

        setStyleName("Добавить новую компанию");

        addCompany.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addCompany.setIcon(VaadinIcons.INSERT);
        addCompany.setSizeFull();

        Button cancel = new Button("Отменить");
        cancel.addClickListener(clickEvent -> close());

        cancel.setSizeFull();

        final int[] companyid = {0};
        final String[] companyNameArr = {""};
        final String[] nipArr = {""};
        final String[] addressArr = {""};
        final String[] phoneArr = {""};

        FieldsCompany fieldsCompany = new FieldsCompany();
        TextField nameTextField = fieldsCompany.getFullNameTextField();
        TextField nipTextField = fieldsCompany.getNipTextField();
        TextField address = fieldsCompany.getAddressTextField();
        TextField phone = fieldsCompany.getPhoneTextField();


        content.addComponents(nameTextField, nipTextField, address, phone, addCompany, cancel);

        setContent(content);

        nameTextField.setRequiredIndicatorVisible(true);
        nipTextField.setRequiredIndicatorVisible(true);
        address.setRequiredIndicatorVisible(true);
        phone.setRequiredIndicatorVisible(true);

        /*
        Заполняем пеменные из поля Имя компании
        */
        nameTextField.addValueChangeListener(valueChangeEvent ->
                companyNameArr[0] = valueChangeEvent.getValue()
        );
         /*
        Заполняем пеменные из поля ИНН компании
        */
        nipTextField.addValueChangeListener(valueChangeEvent ->
                nipArr[0] = valueChangeEvent.getValue());
        /*
        Заполняем пеменные из поля Адрес компании
        */
        address.addValueChangeListener(valueChangeEvent ->
                addressArr[0] = valueChangeEvent.getValue());
         /*
        Заполняем пеменные из поля Телефон компании
        */
        phone.addValueChangeListener(valueChangeEvent ->
                phoneArr[0] = valueChangeEvent.getValue());

         /*
         Добавляем компанию, если такой нет в БД и если правильно заполнены все поля
        */
        addCompany.addClickListener((Button.ClickListener) clickEvent6 -> {
            Company company;


            if (!(companyNameArr[0].isEmpty() || !CheckNIP.isValidINN(nipArr[0])
                    || addressArr[0].isEmpty() || !CheckPhone.isValidPhone(phoneArr[0]))) {

                try {

                    company = new Company( companyid[0],companyNameArr[0], nipArr[0],
                             addressArr[0], phoneArr[0]);

                    if
                    (companyDao.checkCompanyByName(company.getName())) {
                        logger.warning("Такая компания уже существет " + company.getName());
                        company.setName("");

                    } else {

                        companyDao.insertCompany(company);

                        MainLayout.companyGrid.setItems(companyDao.selectAllCompanies());

                        close();

                    }

                } catch (NumberFormatException ex) {
                    logger.warning("Неверные данные компании " + ex);
                } catch (NullPointerException ex) {
                    logger.warning("NPE " + ex);
                }

            } else {
                fieldsCompany.check(nameTextField);
                fieldsCompany.check(nipTextField);
                fieldsCompany.check(address);
                fieldsCompany.check(phone);
                logger.warning("Необходимо заполнить все данные компании " +
                        companyNameArr[0] + " " + nipArr[0] + " " + addressArr[0] + " " + phoneArr[0]);
            }

        });

    }
}



















