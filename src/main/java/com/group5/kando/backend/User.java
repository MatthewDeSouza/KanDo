package com.group5.kando.backend;

import com.group5.kando.backend.exception.UserHasRoleException;
import com.group5.kando.backend.exception.UserHasTicketException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID uuid = UUID.randomUUID();
    private String email;
    private String password;
    private Set<Role> roles;
    private Set<Ticket> tickets;


    /**
     * Public Constructor for User. Only takes email, and upon creation,
     * password encryption will be handled in a different class for safety reasons.
     * @param email User's email.
     * @author Matthew DeSouza
     */
    public User(String email) {
        this.email = email;
        roles = new HashSet<>();
    }

    /**
     * Takes a {@link Role} object to add to the set of a given User's roles.
     * @param role Role to add to User's role set.
     * @throws UserHasRoleException Supplied {@link Role} is already found in User's role set.
     * @author Matthew DeSouza
     */
    public void addRole(Role role) throws UserHasRoleException {
        if (roles.contains(role)) {
            throw new UserHasRoleException("User already has role [" + role.getName() + "].");
        }
        roles.add(role);
    }

    /**
     * Takes multiple {@link Role} objects to add to the set of a given User's roles.
     * @param roles Roles to add to the User's role set, will ignore roles already in the User's set.
     * @author Matthew DeSouza
     */
    public void addRoles(Role... roles) throws UserHasRoleException {
        this.roles.addAll(Arrays.asList(roles));
    }

    /**
     * Takes a {@link Ticket} object to add to the set of a given User's roles.
     * @param ticket Ticket to add to User's ticket set.
     * @throws UserHasTicketException Supplied {@link Ticket} is already found in User's ticket set.
     * @author Matthew DeSouza
     */
    public void addTicket(Ticket ticket) throws UserHasTicketException {
        if (tickets.contains(ticket)) {
            throw new UserHasTicketException("User already has ticket [" + ticket.getTitle() + "].");
        }
        tickets.add(ticket);
    }

    /**
     * Takes multiple {@link Ticket} objects to add to the set of a given User's tickets.
     * @param tickets Tickets to add to the User's ticket set, will ignore tickets already in the User's set.
     * @author Matthew DeSouza
     */
    public void addTickets(Ticket... tickets) {
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public void removeRoles() {
        roles = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
