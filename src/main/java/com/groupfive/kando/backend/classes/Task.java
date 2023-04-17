package com.groupfive.kando.backend.classes;

import com.groupfive.kando.backend.status.Status;
import java.util.Date;
import java.util.Set;

public class Task {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Set<Employee> assignedTo;
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

    public Set<User> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Set<User> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
