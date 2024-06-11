package com.cordernot.api.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cordernot.entities.Customer;
import com.cordernot.repository.CustomerRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)  // cela va permettre de simuler une fausse DB et mocker des objets
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void CustomerRepository_SaveAll() {

        //Arrange
        Customer customer = Customer.builder().email("test@gmail").name("test").build();

        //Act
        Customer savedCustomer = customerRepository.save(customer);

        //Assert
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getName()).isEqualTo("test");
    }
    
    @Test
    public void CustomerRepoSaveMore() {
        Customer customer = Customer.builder().email("test@gmail").name("test").build();
        Customer customer2 = Customer.builder().email("test@gmail").name("hallo").build();

        customerRepository.save(customer);
        customerRepository.save(customer2);

        List<Customer> customerList = customerRepository.findAll();

        Assertions.assertThat(customerList).isNotNull();
        Assertions.assertThat(customerList.size()).isEqualTo(2);
    }

    @Test
    public void CustomerRepository_SaveWithId() {

        //Arrange
        Customer customer = Customer.builder().email("test@gmail").name("test").build();
        Customer customer2 = Customer.builder().email("test@gmail").name("hallo").build();

        //Act
        customerRepository.save(customer);
        customerRepository.save(customer2);

        Optional<Customer> customerSaved = customerRepository.findById(customer.getId());

        //Assert
        Assertions.assertThat(customerSaved).isNotNull();
        Assertions.assertThat(customerSaved.get().getName()).isEqualTo("test");
    }

    @Test
    public void CustomerRepository_SaveWithCustomeMethodInRepo() {

        //Arrange
        Customer customer = Customer.builder().email("testadress2@gmail").name("test").build();
        Customer customer2 = Customer.builder().email("test@gmail").name("hallo").build();

        //Act
        customerRepository.save(customer);
        customerRepository.save(customer2);

        Optional<Customer> customerSaved = customerRepository.findByEmail(customer.getEmail());

        //Assert
        Assertions.assertThat(customerSaved).isNotNull();
        Assertions.assertThat(customerSaved.get().getName()).isEqualTo("test");
    }
}
