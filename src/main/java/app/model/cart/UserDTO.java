package app.model.cart;

import app.model.enums.UserRole;
import app.model.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;

public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String stringDateOfBirth;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStringDateOfBirth() {
        return stringDateOfBirth;
    }

    public void setStringDateOfBirth(String stringDateOfBirth) {
        this.stringDateOfBirth = stringDateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
