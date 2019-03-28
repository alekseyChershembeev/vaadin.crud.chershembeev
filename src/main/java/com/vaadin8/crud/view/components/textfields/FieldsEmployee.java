package com.vaadin8.crud.view.components.textfields;


import com.AisaTest06.check.fields.CheckMail;
import com.vaadin.server.UserError;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import java.util.logging.Logger;



public class FieldsEmployee extends TextField {

    private static Logger logger = Logger.getLogger(FieldsEmployee.class.getName());

    private TextField emailTextField;

    /*
    Проверка на заполеннность поля ФИО
    */
    public TextField getFullName() {
        TextField fullName = new TextField("ФИО");

        fullName.addValueChangeListener(valueChangeEvent -> {
            if (!(valueChangeEvent.getValue().length()>0)){
                fullName.setComponentError(new UserError("Необходимо ввести имя"));
            }
            else {
                fullName.setComponentError(null);
            }
        });
        fullName.setSizeFull();
        return fullName;
    }
    /*
    Проверка на выбор даты "Дата Рождения"
    */
    public DateField getDateField() {

        DateField dateField = new DateField("Дата рождения");

        dateField.addValueChangeListener(valueChangeEvent -> {
            if (dateField.isEmpty()){
                dateField.setComponentError(new UserError("Необходимо ввести дату"));
            }
            else {
                dateField.setComponentError(null);
            }
        });
        dateField.setSizeFull();


        return dateField;
    }
    /*
    Проверка на заполеннность поля email
    */
    public TextField getEmailTextField() {

        emailTextField = new TextField("email");
        emailTextField.setSizeFull();
        emailTextField.addValueChangeListener(event -> {



            if (!CheckMail.isValidPhone(event.getValue())) {
                emailTextField.setComponentError(new UserError("Нужен email"));
            }
            else if (!(event.getValue().length()>0)){
                emailTextField.setComponentError(new UserError("Необходимо заполнить email"));
            }
            else{
                emailTextField.setComponentError(null);

            }


        });
        return emailTextField;

    }
    /*
    Проверка на заполеннность TextField
    */
    public void check(TextField textField) {

        if (textField.getValue().isEmpty()) {
            textField.setComponentError(new UserError("Необходимо заполнить поле "+ textField.getCaption()));
            logger.warning("Необхадимо заполнить поле "+ textField.getCaption());
        }
        else {
            textField.setComponentError(null);
        }

    }
    /*
    Проверка на заполеннность поля DateField
    */
    public void check(DateField dateField) {
        if (dateField.isEmpty()) {
            dateField.setComponentError(new UserError("Необходимо заполнить поле "+dateField.getCaption() ));
            logger.warning("Необхадимо заполнить дату ");
        }
        else {
            dateField.setComponentError(null);
        }
    }
    /*
    Проверка на заполеннность Combobox
    */
    public void check(ComboBox comboBox) {
        if (comboBox.isEmpty()) {
            comboBox.setComponentError(new UserError("Необходимо "+ comboBox.getCaption()));
            logger.warning("Необходимо "+ comboBox.getCaption());
        }
        else {
            comboBox.setComponentError(null);
        }
    }

}
