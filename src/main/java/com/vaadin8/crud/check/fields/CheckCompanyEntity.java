package com.vaadin8.crud.check.fields;

import com.vaadin8.crud.entity.Company;

import java.util.regex.Pattern;

public class CheckCompanyEntity {
    
    public static boolean checkCompany(Company company){
     
        return (isValidName(company.getName())
                &&isValidAddress(company.getAddress())
                &&isValidINN(company.getNip())
                &&isValidPhone(company.getPhone()));
    }

    public static boolean isValidName(String name){
        return name.length()>1;
    }


    public static boolean isValidAddress(String address) {
        return address.length() > 2;
    }

    private static final String PHONE_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public static boolean isValidPhone(String phone) {
        return phone.matches(PHONE_PATTERN);
    }


//примеры
//        isValidINN("6449013711");
//        isValidINN("7727563778");
//        isValidINN("500100732259");

    /*
        Алгоритм проверки ИНН 10 знаков:
            1. Вычисляется контрольная сумма со следующими весовыми коэффициентами: (2,4,10,3,5,9,4,6,8,0)
            2. Вычисляется контрольное число как остаток от деления контрольной суммы на 11
            3. Если контрольное число больше 9,
             то контрольное число вычисляется как остаток от деления контрольного числа на 10
            4. Контрольное число проверяется с десятым знаком ИНН. В случае их равенства ИНН считается правильным.

        Алгоритм проверки ИНН 12 знаков.
            1. Вычисляется контрольная сумма по 11-ти знакам со следующими весовыми коэффициентами: (7,2,4,10,3,5,9,4,6,8,0)
            2. Вычисляется контрольное число(1) как остаток от деления контрольной суммы на 11
            3. Если контрольное число(1) больше 9,
            то контрольное число(1) вычисляется как остаток от деления контрольного числа(1) на 10
            4. Вычисляется контрольная сумма по 12-ти знакам со следующими весовыми коэффициентами:
            (3,7,2,4,10,3,5,9,4,6,8,0).
            5. Вычисляется контрольное число(2) как остаток от деления контрольной суммы на 11
            6. Если контрольное число(2) больше 9,
            то контрольное число(2) вычисляется как остаток от деления контрольного числа(2) на 10
            7. Контрольное число(1) проверяется с одиннадцатым знаком ИНН и контрольное число(2),
             проверяется с двенадцатым знаком ИНН.
            В случае их равенства ИНН считается правильным.
       */
    private static final Pattern INN_PATTERN = Pattern.compile("\\d{10}|\\d{12}");

    public static boolean isValidINN(String inn) {
        inn = inn.trim();
        if (!INN_PATTERN.matcher(inn).matches()) {
            return false;
        }
        int length = inn.length();
        if (length == 12) {
            return INNStep(inn, 2, 1) && INNStep(inn, 1, 0);
        } else {
            return INNStep(inn, 1, 2);
        }
    }

    private static final int[] checkArr = new int[] {3,7,2,4,10,3,5,9,4,6,8};

    private static boolean INNStep(String inn, int offset, int arrOffset) {
        int sum = 0;
        int length = inn.length();
        for (int i = 0; i < length - offset; i++) {
            sum += (inn.charAt(i) - '0') * checkArr[i + arrOffset];
        }
        return (sum % 11) % 10 == inn.charAt(length - offset) - '0';
    }



}
