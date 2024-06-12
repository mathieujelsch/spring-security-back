package com.cordernot.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor // ces 2 permets d'utiliser builder normalement
@NoArgsConstructor // ces 2 permets d'utiliser builder normalement pour les tests
@Builder
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  private String password;

  private String email;

  @OneToMany(mappedBy = "customer")
  @JsonManagedReference  // ca a corrigé le problème ou que je pouvais faire qu'une seule publication, idem dans publication model
  private List<Publication> publications;
}
