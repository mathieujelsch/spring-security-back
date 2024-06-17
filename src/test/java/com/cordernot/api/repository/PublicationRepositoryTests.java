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

    @Test
    public void DeleteFromPublicationRepoWithoutLike(){

        Customer customer = Customer.builder().email("test@gmail").name("test").build();
        Customer savedCustomer = customerRepository.save(customer);

        Publication publication = Publication.builder().content("hello").customer(savedCustomer).build();
        Publication publication2 = Publication.builder().content("hello2").customer(savedCustomer).build();

        publicationRepository.save(publication);
        publicationRepository.save(publication2);

        publicationRepository.delete(publication);

        Assertions.assertThat(publicationRepository.count()).isEqualTo(1);
        Assertions.assertThat(publicationRepository.findById(publication2.getId())).isPresent();
        Assertions.assertThat(publicationRepository.findById(publication.getId())).isNotPresent();
    }

    @Test
    public void DeleteFromPublicationRepoWithLike(){

        Customer customer = Customer.builder().email("test@gmail").name("test").build();
        Customer savedCustomer = customerRepository.save(customer);

        Publication publication = Publication.builder().content("hello").likes(1).dislikes(1).customer(savedCustomer).build();
        Publication publication2 = Publication.builder().content("hello2").customer(savedCustomer).build();

        publicationRepository.save(publication);
        publicationRepository.save(publication2);

        publicationRepository.delete(publication);

        Assertions.assertThat(publicationRepository.count()).isEqualTo(1);
        Assertions.assertThat(publicationRepository.findById(publication2.getId())).isPresent();
        Assertions.assertThat(publicationRepository.findById(publication.getId())).isNotPresent();
    }
}
