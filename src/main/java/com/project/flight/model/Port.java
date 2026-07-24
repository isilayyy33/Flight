package com.project.flight.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "port")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 3)
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne //Nesne olarak tuttuk yani sadece isim değil
    private City city;
}