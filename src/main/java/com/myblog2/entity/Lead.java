package com.myblog2.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="leads" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Lead {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)


    private long id;
    @Column(name="First_name",nullable = false)
    private String firstName;
    @Column(name="Last_name",nullable = false)
    private String lastName;
    @Column(name="email",unique = true,nullable = false)
    private String email;
    @Column(name="mobile",nullable = false)
    private long mobile;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
