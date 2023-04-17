package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.repository.ResourceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
class ResourceServiceTest {

    private ResourceService resourceService;
    @Mock
    private ResourceRepository resourceRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        resourceService = new ResourceService(resourceRepository);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }




    @Test
    void getResourceById() {
        resourceService.getResourceById(69L);
        Mockito.verify(resourceRepository).findById(69L);
    }

    @Test
    void getResourcesByName() {
        resourceService.getResourcesByName("ayy");
        Mockito.verify(resourceRepository).getResourceByName("ayy");
    }

    @Test
    void getAllResources() {
        resourceService.getAllResources();
        Mockito.verify(resourceRepository).findAll();
    }
    @Test
    void getAllResourcesFromUser(){
        resourceService.getAllResourcesFromUser(69);
        Mockito.verify(resourceRepository).getAllResourcesFromUser(69L);
    }

    @Test
    void getAllPublicResourcesFromUser(){
        resourceService.getAllPublicResourcesFromUser(69);
        Mockito.verify(resourceRepository).getAllPublicResourcesFromUser(69);
    }

    @Test
    void saveResourceForUser() {
        Resource r = new Resource(1,"resName","resDesc","hh", ResVisibility.PUBLIC.name());
        resourceService.saveResourceForUser(r,69);
        Mockito.verify(resourceRepository).save(r);
    }

    @Test
    void updateResource_modificationNotNullAllChanged() {
        Resource r = new Resource(1,"resName","resDesc","hh", ResVisibility.PUBLIC.name());
        Resource r_modif = new Resource(1,"resNameNew","resDescNew","hh", ResVisibility.PRIVATE.name());

        Mockito.when(resourceRepository.findById(1L)).thenReturn(Optional.of(r));
        resourceService.updateResource(r.getResourceId(),r_modif);

        ArgumentCaptor<Resource> captoe = ArgumentCaptor.forClass(Resource.class);
        Mockito.verify(resourceRepository).save(captoe.capture());

        Assertions.assertThat(captoe.getValue().getResourceName())
                .isEqualTo("resNameNew");
        Assertions.assertThat(captoe.getValue().getResourceDescription())
                .isEqualTo("resDescNew");
        Assertions.assertThat(captoe.getValue().getResVisibility())
                .isEqualTo("PRIVATE");
    }

    @Test
    void updateResource_modificationNotNullNothingChanged() {
        Resource r = new Resource(1,"resName","resDesc","hh", ResVisibility.PUBLIC.name());
        Resource r_modif = new Resource(1,"resName","resDesc","hh", ResVisibility.PUBLIC.name());

        Mockito.when(resourceRepository.findById(1L)).thenReturn(Optional.of(r));

        resourceService.updateResource(r.getResourceId(),r_modif);
        ArgumentCaptor<Resource> captoe = ArgumentCaptor.forClass(Resource.class);
        Mockito.verify(resourceRepository).save(captoe.capture());

        Assertions.assertThat(captoe.getValue().getResourceName())
                .isEqualTo("resName");
        Assertions.assertThat(captoe.getValue().getResourceDescription())
                .isEqualTo("resDesc");
        Assertions.assertThat(captoe.getValue().getResVisibility())
                .isEqualTo("PUBLIC");
    }

    @Test
    void updateResource_modificationNull() {
        Resource r = new Resource(1,"resName","resDesc","hh", ResVisibility.PUBLIC.name());

        Mockito.when(resourceRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->resourceService.updateResource(-1,null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("No Input Data");
    }

    @Test
    void updateResource_selectionNull() {
        Resource r_modif = new Resource(1,"resName","resDesc","hh", ResVisibility.PUBLIC.name());

        Mockito.when(resourceRepository.findById(-1L)).thenReturn(Optional.empty());
        resourceService.updateResource(-1,r_modif);
        Mockito.verify(resourceRepository).save(r_modif);
    }

    @Test
    void deleteResource() {
        resourceService.deleteResource(69);
        Mockito.verify(resourceRepository).deleteById(69L);
    }

    @Test
    void deleteAll() {
        resourceService.deleteAll();
        Mockito.verify(resourceRepository).deleteAll();
    }
}