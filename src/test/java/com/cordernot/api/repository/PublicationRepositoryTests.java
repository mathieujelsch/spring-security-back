package com.cordernot.api.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cordernot.entities.Customer;
import com.cordernot.entities.Publication;
import com.cordernot.repository.CustomerRepository;
import com.cordernot.repository.PublicationRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) 
public class PublicationRepositoryTests {
    
    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    
    // public PublicationRepositoryTests(PublicationRepository publicationRepository) {
    //     this.publicationRepository = publicationRepository;
    // } // pas besoin de ca c'est fait par autowired

    @Test
    public void PublicationRepository_SaveAll_ReturnsSavePublication() {

        Customer customer = Customer.builder().email("test@gmail").name("test").build();
        Customer savedCustomer = customerRepository.save(customer);

        Publication publication = Publication.builder().content("wahoo").customer(savedCustomer).build();
        Publication savedPubli = publicationRepository.save(publication);

        //Assert
        Assertions.assertThat(savedPubli).isNotNull();
        Assertions.assertThat(savedPubli.getContent()).isEqualTo("wahoo");
    }
}
