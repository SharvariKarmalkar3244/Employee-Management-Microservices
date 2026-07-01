package com.address.model.dto;

import java.util.List;

public class AddressRequest {

    private Long empId;
    private List<AddressRequestDto> addressRequestDto;

    public AddressRequest() {
    }

    public AddressRequest(Long empId, List<AddressRequestDto> addressRequestDto) {
        this.empId = empId;
        this.addressRequestDto = addressRequestDto;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public List<AddressRequestDto> getAddressRequestDto() {
        return addressRequestDto;
    }

    public void setAddressRequestDto(List<AddressRequestDto> addressRequestDto) {
        this.addressRequestDto = addressRequestDto;
    }

    @Override
    public String toString() {
        return "AddressRequest{" +
                "empId=" + empId +
                ", addressRequestDto=" + addressRequestDto +
                '}';
    }
}