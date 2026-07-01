package com.address.client;

import com.address.config.FeignConfig;
import com.address.model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employeeClient", url = "${employee.service.url}", configuration = FeignConfig.class)
public interface EmployeeClient {

    @GetMapping("/{id}")
    EmployeeDto getSingleEmployee(@PathVariable Long id);

}