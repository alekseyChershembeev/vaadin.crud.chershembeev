package com.vaadin8.crud.view.components.textfields;


import com.vaadin.server.UserError;
import com.vaadin.ui.TextField;
import com.vaadin8.crud.check.fields.CheckCompanyEntity;

import java.util.logging.Logger;

public class FieldsCompany extends TextField {

    private static Logger logger = Logger.getLogger(FieldsCompany.class.getName());


    private TextField nipTextField;
    private TextField phoneTextField;

    public FieldsCompany() {

    }
    /*
    Проверка на заполеннность адреса
   */
    public TextField getAddressTextField()
    {
        TextField addressTextField = new TextField("Адрес");
        addressTextField.addValueChangeListener(valueChangeEvent -> {
            if (CheckCompanyEntity.isValidAddress(valueChangeEvent.getValue())){
                addressTextField.setComponentError(null);
            }
            else {
                addressTextField.setComponentError(
                        new UserError("Необходимо заполнить поле "+ addressTextField.getCaption()));
            }
        });
        addressTextField.setSizeFull();

        return addressTextField;
    }
    /*
    Проверка на заполеннность имени компании
   */
    public TextField getFullNameTextField() {
        TextField fullNameTextField = new TextField("Имя компании");

        fullNameTextField.addValueChangeListener(valueChangeEvent -> {
            if (CheckCompanyEntity.isValidName(valueChangeEvent.getValue())){
                fullNameTextField.setComponentError(null);
            }else {
                fullNameTextField.setComponentError(
                        new UserError("Необходимо заполнить поле "+ fullNameTextField.getCaption()));

            }
        });
        fullNameTextField.setWidth(80, Unit.PERCENTAGE);
        return fullNameTextField;
    }
    /*
    Проверка на заполеннность телефона компании
   */
    public TextField getPhoneTextField() {
        phoneTextField = new TextField("Телефон");
        phoneTextField.setSizeFull();
        phoneTextField.addValueChangeListener(event -> {
            if (CheckCompanyEntity.isValidPhone(event.getValue())) {
                phoneTextField.setComponentError(null);
            } else {
                phoneTextField.setComponentError(
                        new UserError("Должны быть цифры"));
            }
        });
        return phoneTextField;
    }
    /*
    Проверка на заполеннность ИНН компании
   */
    public TextField getNipTextField() {
        nipTextField = new TextField("ИНН");
        nipTextField.setSizeFull();
        nipTextField.addValueChangeListener(event -> {
            if (CheckCompanyEntity.isValidINN(event.getValue())) {
                nipTextField.setComponentError(null);
            } else {
                nipTextField.setComponentError(
                        new UserError("Необходим ИНН от 10 до 12 цифр"));
            }
        });
        return nipTextField;
    }
    /*
    Проверка на заполеннность TextField компании
   */
    public void check(TextField textField) {

        if (textField.getValue().isEmpty()) {
            textField.setComponentError(
                    new UserError("Необходимо заполнить поле "+ textField.getCaption()));
            logger.warning("Пустое поле "+ textField.getCaption());
        } else {
            textField.setComponentError(null);
        }
    }
}




