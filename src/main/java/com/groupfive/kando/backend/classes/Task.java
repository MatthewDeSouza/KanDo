package com.groupfive.kando.backend.classes;

import com.groupfive.kando.backend.exception.EmployeeHasTaskException;
import com.groupfive.kando.backend.status.Status;
import java.util.Date;
import java.util.Set;

public class Task {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;
    private String priority;
    private Set<Employee> employees;

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

    public Status getStatus() {
        return status;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void assignEmployee(Employee employee) throws EmployeeHasTaskException {
        if (employees.contains(employee)) {
            throw new EmployeeHasTaskException("Employee is already assigned to this task!");
        }
        employees.add(employee);
        // Maintain ManyToMany relationship consistency.
        employee.getTasks().add(this);
    }

    public void removeEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            return;
        }
        employees.remove(employee);
        employee.getTasks().remove(this);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
