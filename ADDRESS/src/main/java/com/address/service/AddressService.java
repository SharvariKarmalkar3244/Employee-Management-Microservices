//package com.address.service;
//
//import com.address.model.dto.AddressDto;
//import com.address.model.dto.AddressRequest;
//
//import java.util.List;
//
//public interface AddressService {
//    public List<AddressDto> saveAddress(AddressRequest addressRequest);
//
//    List<AddressDto> updateAddress(AddressRequest addressRequest);
//
//    public AddressDto getSingleAddress(Long id);
//
//    public List<AddressDto> getAllAddresses();
//
//    public void deleteAddress(Long id);
//}
package com.address.service;

import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);

    List<AddressDto> updateAddress(AddressRequest addressRequest);

    AddressDto getSingleAddress(Long id);

    List<AddressDto> getAllAddress();

    List<AddressDto> getAddressByEmpId(Long empId);

    void deleteAddress(Long id);
}