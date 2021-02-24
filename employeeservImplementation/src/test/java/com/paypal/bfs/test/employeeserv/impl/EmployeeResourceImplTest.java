package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeResourceImplTest {
    private final int ID_1 = 1;

    @Mock
    EmployeeRepository employeeRepository;
    Employee employee;

    EmployeeResource employeeResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employee = new Employee();
        employeeResource = new EmployeeResourceImpl(employeeRepository);
    }

    @Test
    public void employGetById_with_storedID_should_returnCorrectResult() {
        when(employeeRepository.findById(ID_1)).thenReturn(Optional.ofNullable(employee));
        Assert.assertEquals(employee, employeeResource.employeeGetById(ID_1).getBody());
    }

    @Test
    public void employeeGetById_with_nonExistentID_should_returnNotFound() {
        when(employeeRepository.findById(ID_1)).thenReturn(Optional.empty());
        Assert.assertEquals(HttpStatus.NOT_FOUND, employeeResource.employeeGetById(ID_1).getStatusCode());
    }

    @Test
    public void createEmployee_should_saveObject() {
        employeeResource.createEmployee(employee);
        Mockito.verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void createEmployee_with_existingObject_should_returnConflictStatus() {
        when(employeeRepository.findOne(Example.of(employee))).thenReturn(Optional.ofNullable(employee));
        Assert.assertEquals(HttpStatus.CONFLICT, employeeResource.createEmployee(employee).getStatusCode());
    }
}
