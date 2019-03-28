package com.vaadin8.crud.view.windows;

import com.AisaTest06.check.fields.CheckNIP;
import com.AisaTest06.check.fields.CheckPhone;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.EmployeeDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.dao.dao.interfaces.EmployeeDao;
import com.vaadin8.crud.entity.Company;
import com.vaadin8.crud.view.components.layouts.MainLayout;
import com.vaadin8.crud.view.components.textfields.FieldsCompany;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class EditCompanyWindow extends Window {

    private static Logger logger = Logger.getLogger(EditCompanyWindow.class.getName());


    public EditCompanyWindow(Company company) {
        setStyleName("Редактировать компанию");
        VerticalLayout editWindowLayout = new VerticalLayout();

        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setContent(editWindowLayout);
        setWidth(400f,Unit.PIXELS);
        setHeight(420,Unit.PIXELS);
        setResizeLazy(false);


        CompanyDao companyDao = new CompanyDaoImpl();
        EmployeeDao employeeDao = new EmployeeDaoImpl();

        Button editCompany = new Button("Редактировать");
        editCompany.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        editCompany.setSizeFull();
        editCompany.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        editCompany.setIcon(VaadinIcons.EDIT);

        Button cancel = new Button("Отменить",clickEvent -> close());

        cancel.setSizeFull();
        FieldsCompany fieldsCompany = new FieldsCompany();

        TextField nameField = fieldsCompany.getFullNameTextField();
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        nameField.setSizeFull();
        TextField nipField = fieldsCompany.getNipTextField();
        TextField addressField = fieldsCompany.getAddressTextField();
        TextField phoneField = fieldsCompany.getPhoneTextField();


        final int[] companyidArr = {0};
        final String[] nameArr = {""};
        final String[] NIPArr = {""};
        final String[] addressArr = {""};
        final String[] phoneArr = {""};


        nameField.setRequiredIndicatorVisible(true);
        nipField.setRequiredIndicatorVisible(true);
        addressField.setRequiredIndicatorVisible(true);
        phoneField.setRequiredIndicatorVisible(true);


        companyidArr[0]=company.getCompanyId();
        nameArr[0] =company.getName();
        NIPArr[0] = String.valueOf(company.getNip());
        addressArr[0]=company.getAddress();
        phoneArr[0] = String.valueOf(company.getPhone());


        nameField.setValue(company.getName());
        nipField.setValue(String.valueOf(company.getNip()));
        addressField.setValue(company.getAddress());
        phoneField.setValue(String.valueOf(company.getPhone()));

        /*
        Заполняем пеменные из поля Имя компании
        */
        nameField.addValueChangeListener(valueChangeEvent ->
                nameArr[0] = valueChangeEvent.getValue()
        );
         /*
        Заполняем пеменные из поля ИНН компании
        */
        nipField.addValueChangeListener(valueChangeEvent ->
                NIPArr[0] = valueChangeEvent.getValue());
        /*
        Заполняем пеменные из поля Адрес компании
        */
        addressField.addValueChangeListener(valueChangeEvent ->
                addressArr[0] = valueChangeEvent.getValue());
          /*
        Заполняем пеменные из поля Телефон компании
        */
        phoneField.addValueChangeListener(valueChangeEvent ->
                phoneArr[0] = valueChangeEvent.getValue());
        /*
        Заполняем пеменные из поля Имя компании
        */




        editWindowLayout.addComponents(nameField, addressField, nipField, phoneField, editCompany, cancel);

         /*
         Редактируем компанию, если такой нет в БД и если заполнены правильно все поля
         Редактируем у сотрудника название компании при изменении компании
        */
        editCompany.addClickListener((Button.ClickListener) clickEvent15 -> {

            try {

                company.setCompanyId(companyidArr[0]);
                company.setName(nameArr[0]);
                company.setNip(NIPArr[0]);
                company.setAddress(addressArr[0]);
                company.setPhone(phoneArr[0]);

//                if
//                (companyDao.checkCompanyByName(company.getName())) {
//                    logger.warning("Компания с таким именем уже существует " + company.getName());
//
//                } else

                if (!(nameArr[0].isEmpty()||(!CheckNIP.isValidINN(NIPArr[0]))
                        || addressArr[0].isEmpty() || !CheckPhone.isValidPhone(phoneArr[0]))) {

                    companyDao.editCompany(company);
                    ((EmployeeDaoImpl) employeeDao).editEmployeeName(company);

                    MainLayout.companyGrid.setItems(companyDao.selectAllCompanies());

                    close();
                } else {
                    fieldsCompany.check(nameField);
                    fieldsCompany.check(nipField);
                    fieldsCompany.check(addressField);
                    fieldsCompany.check(phoneField);
                    logger.warning("Неверные данные компании " + company);

                }
            } catch (NumberFormatException ex) {
                logger.warning("Неверная редакция компании " + ex + " " + company);
            } catch (NullPointerException ex) {
                logger.warning("NPE компании " + company + " " + ex);
            }
        });


    }

}

