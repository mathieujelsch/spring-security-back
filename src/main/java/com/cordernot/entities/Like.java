// package com.cordernot.entities;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Data
// @AllArgsConstructor // ces 2 permets d'utiliser builder normalement
// @NoArgsConstructor // ces 2 permets d'utiliser builder normalement pour les tests
// @Builder
// public class Like {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @JoinColumn(name = "customer_id")
//     private Customer customer;

//     @ManyToOne
//     @JoinColumn(name = "publication_id")
//     private Publication publication;

//     // // constructors, getters, setters
//     // public Like() {}

//     // public Like(Customer customer, Publication publication) {
//     //     this.customer = customer;
//     //     this.publication = publication;
//     // }
// }
