package com.address.service.impl;

import com.address.client.EmployeeClient;
import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.model.dto.AddressRequestDto;
import com.address.model.entity.Address;
import com.address.repository.AddressRepository;
import com.address.service.AddressService;
import com.commonlib.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final EmployeeClient employeeClient;

    public AddressServiceImpl(AddressRepository addressRepository,
                              ModelMapper modelMapper,
                              EmployeeClient employeeClient) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.employeeClient = employeeClient;
    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        employeeClient.getSingleEmployee(addressRequest.getEmpId());
        List<Address> listToSave = this.saveOrUpdateAddressRequest(addressRequest);
        List<Address> savedAddresses = addressRepository.saveAll(listToSave);
        return savedAddresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        Long empId = addressRequest.getEmpId();
        employeeClient.getSingleEmployee(addressRequest.getEmpId());
        List<Address> addressByEmpId =
                addressRepository.findAllByEmpId(addressRequest.getEmpId());
        if (addressByEmpId.isEmpty()) {
            log.info("No address found for employee id {}", addressRequest.getEmpId());
            log.info("Creating new address for employee id {}", addressRequest.getEmpId());
        }
        List<Address> listToUpdate = this.saveOrUpdateAddressRequest(addressRequest);
        List<Long> upcomingNonNullIds = listToUpdate.stream()
                .map(Address::getId)
                .filter(Objects::nonNull)
                .toList();
        List<Long> existingNonNullIds = addressByEmpId.stream()
                .map(Address::getId)
                .filter(Objects::nonNull)
                .toList();
        List<Long> idsToDelete = existingNonNullIds.stream()
                .filter(id -> !upcomingNonNullIds.contains(id))
                .toList();
        if (!idsToDelete.isEmpty()) {
            addressRepository.deleteAllById(idsToDelete);
        }
        List<Address> updatedAddresses = addressRepository.saveAll(listToUpdate);
        return updatedAddresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public List<AddressDto> getAddressByEmpId(Long empId) {
        List<Address> addressByEmpId = addressRepository.findAllByEmpId(empId);
        if (addressByEmpId.isEmpty()) {
            throw new ResourceNotFoundException("No address found for employee id: " + empId, HttpStatus.NOT_FOUND);
        }
        return addressByEmpId.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
//        EmployeeDto employeeDto = employeeClient.getSingleEmployee(empId);
//        return addressByEmpId.stream()
//                .map(address -> {
//                    AddressDto addressDto = modelMapper.map(address, AddressDto.class);
//                    addressDto.setEmpName(employeeDto.getEmpName());
//                    addressDto.setEmpEmail(employeeDto.getEmpEmail());
//                    addressDto.setEmpCode(employeeDto.getEmpCode());
//                    addressDto.setCompanyName(employeeDto.getCompanyName());
//                    return addressDto;
//                })
//                .toList();
    }

    @Override
    public AddressDto getSingleAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found with id: " + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        List<Address> all = addressRepository.findAll();
        if (all.isEmpty()){
            throw new ResourceNotFoundException("No addresses found", HttpStatus.NOT_FOUND);
        }
        return all.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: "+id, HttpStatus.NOT_FOUND));
        addressRepository.delete(address);
    }

    private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest) {

        List<Address> listToSave = new ArrayList<>();

        if (addressRequest.getAddressRequestDto() == null) {
            return listToSave;
        }

        for (AddressRequestDto dto : addressRequest.getAddressRequestDto()) {

            Address address = new Address();

            address.setId(dto.getId());
            address.setEmpId(addressRequest.getEmpId());
            address.setStreet(dto.getStreet());
            address.setPinCode(dto.getPinCode());
            address.setCity(dto.getCity());
            address.setState(dto.getState());
            address.setCountry(dto.getCountry());
            address.setAddressType(dto.getAddressType());

            listToSave.add(address);
        }

        return listToSave;
    }
}