package com.employee.service.impl;

import com.commonlib.exception.BadRequestException;
import com.commonlib.exception.ResourceNotFoundException;
import com.employee.client.AddressClient;
import com.employee.model.dto.AddressDto;
import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final AddressClient addressClient;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ModelMapper modelMapper,
                               AddressClient addressClient) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.addressClient = addressClient;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        if (employeeDto.getId() != null) {
            throw new BadRequestException("Employee already exists");
        }

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setCreatedAt(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);

        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

        if (id == null || employeeDto.getId() == null) {
            throw new BadRequestException("Please provide employee id");
        }

        if (!Objects.equals(id, employeeDto.getId())) {
            throw new BadRequestException("Employee id mismatch");
        }

        employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id " + id,
                                HttpStatus.NOT_FOUND));

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setCreatedAt(LocalDateTime.now());
        Employee updatedEmployee = employeeRepository.save(employee);

        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id " + id,
                                HttpStatus.NOT_FOUND));

        employeeRepository.delete(employee);
    }

@Override
public EmployeeDto getSingleEmployee(Long id) {
//    try {
//        Thread.sleep(6000);
//    } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//    }
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id " + id,
                            HttpStatus.NOT_FOUND));

//    EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);
    List<AddressDto> addresses = new ArrayList<>();
    EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);
    try {
        addresses = addressClient.getAddressByEmpId(employee.getId());
        dto.setAddressDto(addresses);
    } catch (Exception e) {
        log.error("Address Not Found with employee id: {}", employee.getId());
    }
    return dto;
}

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found", HttpStatus.NOT_FOUND);
        }
        List<EmployeeDto> employeeDtoList = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();
        List<EmployeeDto> response = new ArrayList<>();
        for (EmployeeDto employee : employeeDtoList) {
            List<AddressDto> addresses = new ArrayList<>();
            EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);
            try {
                addresses = addressClient.getAddressByEmpId(employee.getId());
                employee.setAddressDto(addresses);
//                if (addresses != null && !addresses.isEmpty()) {
//                    dto.setAddressDto(addresses);
//                }
            } catch (Exception e) {
                log.error("Address not found with employee id: {}", employee.getId());
            }
            response.add(employee);
        }
        return response;
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {

        Employee employee = employeeRepository
                .findByEmpCodeAndCompanyName(empCode, companyName)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with empCode: "
                                        + empCode
                                        + " and companyName: "
                                        + companyName,
                                HttpStatus.NOT_FOUND));

        return modelMapper.map(employee, EmployeeDto.class);
    }
}