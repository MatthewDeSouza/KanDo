package com.groupfive.kando.backend.classes;

import com.groupfive.kando.backend.exception.EmployeeHasProjectException;
import com.groupfive.kando.backend.status.Status;
import java.util.Date;
import java.util.Set;

public class Project {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Set<Ticket> tickets;
    private Set<Employee> employees;
    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addEmployee(Employee employee) throws EmployeeHasProjectException {
        if (employees.contains(employee)) {
            throw new EmployeeHasProjectException("Employee already apart of the project!");
        }
        employees.add(employee);
        employee.getProjects().add(this);
    }

    public void removeEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            return;
        }
        employees.remove(employee);
        employee.getProjects().remove(this);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
