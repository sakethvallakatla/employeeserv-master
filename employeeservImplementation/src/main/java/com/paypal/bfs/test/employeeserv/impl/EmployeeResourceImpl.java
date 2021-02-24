package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
@RequestMapping("/v1/bfs")
public class EmployeeResourceImpl implements EmployeeResource {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeResourceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(int id) {
        final Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Object> createEmployee(Employee employee) {
        if (!employeeRepository.findOne(Example.of(employee)).isPresent()) {
            try {
                final Employee savedEntity = employeeRepository.save(employee);
                return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
            } catch (Exception ex) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
