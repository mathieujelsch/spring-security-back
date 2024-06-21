package com.cordernot.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor // ces 2 permets d'utiliser builder normalement
@NoArgsConstructor // ces 2 permets d'utiliser builder normalement pour les tests
@Builder
public class Publication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String content;

  private int likes;

  private int dislikes;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonBackReference
  private Customer customer;

  @OneToMany
  private List<Comment> comments = new ArrayList<>();

  public Long getCustomerId() {
    return customer != null ? customer.getId() : null;
  }

}
