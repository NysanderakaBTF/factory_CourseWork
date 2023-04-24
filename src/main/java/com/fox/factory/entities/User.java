package com.fox.factory.entities;

import com.fox.factory.security.Role;
import com.fox.factory.security.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Set;

/**
 * This class is a model class that represents a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "customer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @OneToMany
    private Set<Comment> usersComments;

    @OneToMany
    @JoinColumn
    private Set<Order> orders;

    @OneToMany
    private Set<AttendanceTicket> tickets;

    @Size(min = 5)
    @NotBlank
    @Column(unique = true, length = 100)
    private String username;

    @Column
    private String firstName;

    @Column
    private String address;

    @Column(unique = true)
    @Email
    private String email;

    @Column
    @NotBlank
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    private String lastName;

/**
 * If the object is not null and the id is not null and the id of the object is equal to the id of the
 * object passed in, then return true
 * 
 * @param o the object to compare to
 * @return The hashcode of the object.
 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

/**
 * This function adds an order to the user's list of orders
 * 
 * @param order The order to be added to the user's list of orders.
 * @return The User object.
 */
    public User addOrder(Order order){
        this.orders.add(order);
        return this;
    }

}
