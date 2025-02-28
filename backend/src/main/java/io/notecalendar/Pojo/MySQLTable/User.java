package io.notecalendar.Pojo.MySQLTable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;

public class User {
    private Integer id;
    @Pattern(regexp = "^\\S{4,10}$", groups = addUser.class)
    private String username;
//    1 capital, 1number, > 5
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+={}:;,.<>?]{6,}$", groups = {addUser.class, loginUser.class})
    private String password;
    @Email
    @NotEmpty(groups = {addUser.class, loginUser.class})
    private String email;

    public User() {
    }

    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public interface addUser extends Default {

    }
    public interface loginUser extends Default {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
