package com.groupfive.kando.backend.classes;

import com.groupfive.kando.backend.exception.TicketAlreadyCompleteException;
import com.groupfive.kando.backend.status.Status;
import java.util.Set;

public class Ticket {
    private String title;
    private String content;
    private Status status;
    private String type;
    private Set<Task> tasks;
    private Set<Bug> bugs;
    private Set<Employee> assignedTo;

    /**
     * Constructor to create a Ticket, and assign it to the {@link Status} backlog.
     *
     * @param title   Title of ticket.
     * @param content Content of ticket.
     */
    public Ticket(String title, String content) {
        this.title = title;
        this.content = content;
        status = Status.BACKLOG;
    }

    /**
     * Constructor to assign all variables contained within the class.
     *
     * @param title Title of the ticket.
     * @param content Content of the ticket.
     * @param status Status of the ticket.
     * @author Matthew DeSouza
     */
    public Ticket(String title, String content, Status status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    /**
     * Increments the ticket according to the current {@link Status} assigned to it.
     *
     * @throws TicketAlreadyCompleteException Thrown when the Ticket is already of {@link Status} DONE.
     * @author Matthew DeSouza
     */
    public void incrementTicket() throws TicketAlreadyCompleteException {
        switch (status) {
            case BACKLOG:
                status = Status.TODO;
                break;
            case TODO:
                status = Status.WORKING;
                break;
            case WORKING:
                status = Status.DONE;
                break;
            case DONE:
                throw new TicketAlreadyCompleteException("Ticket is already done! [status=" + status.name() + "].");
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(Set<Bug> bugs) {
        this.bugs = bugs;
    }

    public Set<Employee> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Set<Employee> assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n[%s]", title, content, type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

}
