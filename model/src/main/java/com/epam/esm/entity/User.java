package com.epam.esm.entity;



import com.epam.esm.audit.UserAuditingListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class {@code User} represents user entity.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Entity
@Table(name = "users")
@EntityListeners(UserAuditingListener.class)
public class User extends Identifiable {

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(String email, String password, Role role, String name) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public User(long id, String email, String password, Role role, String name) {
        super(id);
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && role == that.role
                && Objects.equals(name, that.name) && Objects.equals(orders, that.orders) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, role, name, orders);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("User{");
        result.append("id=").append(super.getId());
        result.append(", email='").append(email).append('\'');
        result.append(", password='").append(password).append('\'');
        result.append(", role=").append(role);
        result.append(", name='").append(name).append('\'');
        result.append(", orders=").append(orders);
        result.append('}');
        return result.toString();
    }
}
