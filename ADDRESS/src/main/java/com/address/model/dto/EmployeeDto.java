package com.address.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmployeeDto {

    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;

    @JsonProperty("address")
    private List<AddressDto> addressDto;

    // Default Constructor (Required)
    public EmployeeDto() {
    }

    // Parameterized Constructor
    public EmployeeDto(Long id, String empName, String empEmail, String empCode, String companyName) {
        this.id = id;
        this.empName = empName;
        this.empEmail = empEmail;
        this.empCode = empCode;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<AddressDto> getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(List<AddressDto> addressDto) {
        this.addressDto = addressDto;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", empCode='" + empCode + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}