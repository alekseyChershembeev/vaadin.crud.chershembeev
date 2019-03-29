package com.vaadin8.crud.entity;


import java.util.Objects;

public class Company {
    private int companyId;
    private String name;
    private String nip;
    private String address;
    private String phone;

    public Company() {
        this.name="";
        this.nip="";
        this.address="";
        this.phone="";
    }

    public Company(int companyId, String name, String nip, String address, String phone) {
        this.companyId = companyId;
        this.name = name;
        this.nip = nip;
        this.address = address;
        this.phone = phone;
    }

    public Company(String name, String nip, String address, String phone) {
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public java.lang.String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", name=" + name +
                ", nip=" + nip +
                ", address=" + address +
                ", phone=" + phone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId &&
                Objects.equals(name, company.name) &&
                Objects.equals(nip, company.nip) &&
                Objects.equals(address, company.address) &&
                Objects.equals(phone, company.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, name, nip, address, phone);
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


