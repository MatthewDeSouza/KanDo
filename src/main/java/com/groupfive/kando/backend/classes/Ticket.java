package com.groupfive.kando.backend.classes;

/**
 * The Ticket class holds project task information.
 * @author Chris Masoud
 * @author Matthew DeSouza
 */
public class Ticket {
    private String title;
    private String content;
    private String status;
    private String type;

    /**
     * Constructor to create a Ticket, and assign it to the {@link Status} backlog.
     *
     * @param title   Title of ticket.
     * @param content Content of ticket.
     */
    public Ticket(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = "To Do";
        this.type = "";
    }

    /**
     * Constructor to assign all variables contained within the class.
     *
     * @param title Title of the ticket.
     * @param content Content of the ticket.
     * @param status Status of the ticket.
     * @param type Type of the ticket.
     */
    public Ticket(String title, String content, String status, String type) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.type = type;
    }

    /**
     * The getTitle() method returns the title of the current instance.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setTitle() method sets the current title to the string passed in.
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getContent() method returns the content of the current instance.
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * The setContent() method sets the current content to the string passed in.
     * @param content 
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * The getType() method returns the type of the current instance.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * The setType() method sets the current type to the string passed in.
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getStatus() method returns the title of the current instance.
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * The setStatus() method sets the current status to the string passed in.
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * The toString() method override provides a clean display of ticket 
     * information.
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s\n%s\n[%s]", title, content, type);
    }
    
}
