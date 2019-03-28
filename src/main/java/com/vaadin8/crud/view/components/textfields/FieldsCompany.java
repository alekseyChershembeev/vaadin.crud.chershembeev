package com.vaadin8.crud.view.components.textfields;


import com.AisaTest06.check.fields.CheckNIP;
import com.AisaTest06.check.fields.CheckPhone;
import com.vaadin.server.UserError;
import com.vaadin.ui.TextField;

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
            if (!(valueChangeEvent.getValue().length()>0)){
                addressTextField.setComponentError(
                        new UserError("Необходимо заполнить поле "+ addressTextField.getCaption()));
            }
            else {
                addressTextField.setComponentError(null);
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
            if (!(valueChangeEvent.getValue().length()>0)){
                fullNameTextField.setComponentError(
                        new UserError("Необходимо заполнить поле "+ fullNameTextField.getCaption()));
            }else {
                fullNameTextField.setComponentError(null);
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
            if (!CheckPhone.isValidPhone(event.getValue())) {
                phoneTextField.setComponentError(
                        new UserError("Должны быть цифры"));
            } else {
                phoneTextField.setComponentError(null);

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
            if (!CheckNIP.isValidINN(event.getValue())) {
                nipTextField.setComponentError(
                        new UserError("Необходим ИНН от 10 до 12 цифр"));
            } else {
                nipTextField.setComponentError(null);

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




