package com.cordernot.api.controller;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cordernot.controllers.PublicationController;
import com.cordernot.dto.PublicationRequest;
import com.cordernot.entities.Customer;
import com.cordernot.entities.Publication;
import com.cordernot.repository.CustomerRepository;
import com.cordernot.repository.DislikeRepository;
import com.cordernot.repository.LikeRepository;
import com.cordernot.repository.PublicationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PublicationControllerTests {

     @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private DislikeRepository dislikeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PublicationController publicationController;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeletePublication() {
        Long publicationId = 1L;
        Customer customer = Customer.builder().email("test@gmail.com").name("test").build();
        Publication publication = Publication.builder().id(publicationId).content("hello").customer(customer).build();

        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publication));
        when(likeRepository.findByPublicationId(publicationId)).thenReturn(Collections.emptyList());
        when(dislikeRepository.findByPublicationId(publicationId)).thenReturn(Collections.emptyList());
        // doNothing().when(likeRepository).delete(any());
        // doNothing().when(dislikeRepository).delete(any());
        // doNothing().when(publicationRepository).delete(any());

        // Act
        ResponseEntity<Void> response = publicationController.deletePublication(publicationId);

        // Assert
        verify(publicationRepository, times(1)).findById(publicationId);
        verify(likeRepository, times(1)).findByPublicationId(publicationId);
        verify(dislikeRepository, times(1)).findByPublicationId(publicationId);

        verify(publicationRepository, times(1)).delete(any());

        assertEquals(null, response.getBody());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Optional<Publication> deletedPublication = publicationRepository.findById(publicationId);
        // Assertions.assertThat(deletedPublication).isEmpty(); 
    }

    @Test
    public void testDeletePublicationWithLike() {
        Long publicationId = 1L;
        Customer customer = Customer.builder().email("test@gmail.com").name("test").build();
        Publication publication = Publication.builder().id(publicationId).content("hello").likes(1).dislikes(1).customer(customer).build();

        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publication));
        // when(likeRepository.findByPublicationId(publicationId)).thenReturn(Collections.emptyList());
        // when(dislikeRepository.findByPublicationId(publicationId)).thenReturn(Collections.emptyList());
        // doNothing().when(likeRepository).delete(any());
        // doNothing().when(dislikeRepository).delete(any());
        // doNothing().when(publicationRepository).delete(any());

        // Act
        ResponseEntity<Void> response = publicationController.deletePublication(publicationId);

        // Assert
        verify(publicationRepository, times(1)).findById(publicationId);
        verify(likeRepository, times(1)).findByPublicationId(publicationId);
        verify(dislikeRepository, times(1)).findByPublicationId(publicationId);

        verify(publicationRepository, times(1)).delete(any());

        assertEquals(null, response.getBody());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdatePublication() {
        // Arrange
        Long publicationId = 1L;
        PublicationRequest publicationRequest = new PublicationRequest();
        publicationRequest.setContent("Updated content");

        Publication existingPublication = new Publication();
        existingPublication.setId(publicationId);
        existingPublication.setContent("Original content");

        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(existingPublication));
        when(publicationRepository.save(any(Publication.class))).thenReturn(existingPublication);

        // Act
        ResponseEntity<Publication> response = publicationController.updatePublication(publicationId, publicationRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated content", response.getBody().getContent());

        verify(publicationRepository, times(1)).findById(publicationId);
        verify(publicationRepository, times(1)).save(existingPublication);
    }

}
