package com.vaadin8.crud.check.fields;

@SuppressWarnings("ALL")
public class CheckMail {

    public static boolean isValidPhone(String mail){

        String emailTextField_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return mail.matches(emailTextField_PATTERN);
    }

}
