package com.vaadin8.crud.check.fields;

import com.vaadin8.crud.entity.Employee;

public class CheckEmployeeEntity {


    public static boolean checkEmployee(Employee employee){


        return (isValidName(employee.getFullName())
                &&isValidDate(employee.getBirthDate())
                &&isValidMail(employee.getEmail()));
    }


    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidMail(String mail){

        return mail.matches(EMAIL_PATTERN);

    }

    private static final String NAME_PATTERN = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";

    public static boolean isValidName(String name){
        return name.matches(NAME_PATTERN);
    }

    private static final String BIRTH_DATE_PATTERN ="([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

    public static boolean isValidDate(String name){
        return name.matches(BIRTH_DATE_PATTERN);
    }



}
