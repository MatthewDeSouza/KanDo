package com.groupfive.kando.backend.classes;

import java.util.Set;
import java.util.UUID;

/**
 * A class representing an employee assigned to a {@link Task}.
 * <br/>
 *
 * @author Matthew DeSouza
 */
public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private long employeeId;

    private Set<Task> tasks;
    private Set<Project> projects;

    /**
     * Full-arg constructor.
     * @param firstName First name of employee
     * @param lastName Last name of employee
     * @param email Email of employee
     * @param employeeId ID number of employee.
     */
    public Employee(String firstName, String lastName, String email, long employeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeId = employeeId;
    }

    /**
     * Required-arg constructor, automatically generates a unique employeeId long for the object.
     * This constructor is preferred to be used when creating new Employees.
     * @param firstName First name of employee.
     * @param lastName Last name of employee.
     * @param email Email of employee
     */
    public Employee(String firstName, String lastName, String email) {
        this(firstName, lastName, email, UUID.randomUUID().getLeastSignificantBits());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Set<Project> getProjects() {
        return projects;
    }
}
