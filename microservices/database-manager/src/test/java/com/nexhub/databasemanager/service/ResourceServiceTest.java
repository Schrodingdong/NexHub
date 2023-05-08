package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.repository.ResourceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class ResourceServiceTest {
//
//    private ResourceService resourceService;
//    @Mock
//    private ResourceRepository resourceRepository;
//    private AutoCloseable autoCloseable;
//
//    @BeforeEach
//    void setUp(){
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        resourceService = new ResourceService(resourceRepository);
//    }
//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }
//
//
//
//
//    @Test
//    void getResourceById() {
//        resourceService.getResourceById(69L);
//        Mockito.verify(resourceRepository).getResourceById(69L);
//    }
//
//    @Test
//    void getResourcesByName() {
//        resourceService.getResourcesByName("ayy");
//        Mockito.verify(resourceRepository).getResourceByName("ayy");
//    }
//
//    @Test
//    void getAllResources() {
//        resourceService.getAllResources();
//        Mockito.verify(resourceRepository).getAllResources();
//    }
//    @Test
//    void getAllResourcesFromUser(){
//        resourceService.getAllResourcesFromUser(69);
//        Mockito.verify(resourceRepository).getAllResourcesFromUser(69L);
//    }
//
//    @Test
//    void getAllPublicResourcesFromUser(){
//        resourceService.getAllPublicResourcesFromUser(69);
//        Mockito.verify(resourceRepository).getAllPublicResourcesFromUser(69);
//    }
//
//    @Test
//    @Disabled
//    void saveResourceForUser() {
//        Resource r = new Resource("resName","resDesc", ResVisibility.PUBLIC.name());
//        r = resourceService.saveResourceForUser(r,69);
//        Mockito.verify(resourceRepository).saveToGraph(r.getResourceName(),r.getResourceDescription(), r.getResourceBucketId(), r.getResourceVisibility());
//        Mockito.verify(resourceRepository).linkToUser(r.getResourceId(), 69);
//    }
//
//    @Test
//    void updateResource_modificationNotNullAllChanged() {
//        Resource r = new Resource("resName","resDesc", ResVisibility.PUBLIC.name());
//        Resource r_modif = new Resource("resNameNew","resDescNew", ResVisibility.PRIVATE.name());
//
//        Mockito.when(resourceRepository.getResourceById(r.getResourceId())).thenReturn(r);
//        resourceService.updateResource(r.getResourceId(),r_modif);
//
//        ArgumentCaptor<Long> captoe = ArgumentCaptor.forClass(Long.class);
//        ArgumentCaptor<String> captoe1 = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> captoe2 = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> captoe3 = ArgumentCaptor.forClass(String.class);
//        Mockito.verify(resourceRepository).updateResource(captoe.capture(), captoe1.capture(), captoe2.capture(),captoe3.capture());
//
//        Assertions.assertThat(captoe1.getValue())
//                .isEqualTo("resNameNew");
//        Assertions.assertThat(captoe2.getValue())
//                .isEqualTo("resDescNew");
//        Assertions.assertThat(captoe3.getValue())
//                .isEqualTo("PRIVATE");
//    }
//
//    @Test
//    void updateResource_modificationNotNullNothingChanged() {
//        Resource r = new Resource("resName","resDesc",ResVisibility.PUBLIC.name());
//        Resource r_modif = new Resource("resName","resDesc", ResVisibility.PUBLIC.name());
//        Mockito.when(resourceRepository.getResourceById(r.getResourceId())).thenReturn(r);
//        resourceService.updateResource(r.getResourceId(),r_modif);
//
//        ArgumentCaptor<Long> captoe = ArgumentCaptor.forClass(Long.class);
//        ArgumentCaptor<String> captoe1 = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> captoe2 = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> captoe3 = ArgumentCaptor.forClass(String.class);
//        Mockito.verify(resourceRepository).updateResource(captoe.capture(), captoe1.capture(), captoe2.capture(),captoe3.capture());
//
//        Assertions.assertThat(captoe1.getValue())
//                .isEqualTo("resName");
//        Assertions.assertThat(captoe2.getValue())
//                .isEqualTo("resDesc");
//        Assertions.assertThat(captoe3.getValue())
//                .isEqualTo("PUBLIC");
//    }
//
//    @Test
//    void updateResource_modificationNull() {
//        Resource r = new Resource("resName","resDesc", ResVisibility.PUBLIC.name());
//
//        Mockito.when(resourceRepository.findById(-1L)).thenReturn(Optional.empty());
//        Assertions.assertThatThrownBy(()->resourceService.updateResource(-1,null))
//                .isInstanceOf(BadRequestException.class)
//                .hasMessageContaining("No Input Data");
//    }
//
//    @Test
//    void updateResource_selectionNull() {
//        Resource r_modif = new Resource("resName","resDesc", ResVisibility.PUBLIC.name());
//        Mockito.when(resourceRepository.findById(-1L)).thenReturn(Optional.empty());
//        Assertions.assertThatThrownBy(()->resourceService.updateResource(-1,r_modif))
//                .isInstanceOf(BadRequestException.class)
//                .hasMessageContaining("Resource doesn't exist");
//    }
//
//    @Test
//    void deleteResource() {
//        resourceService.deleteResource(69);
//        Mockito.verify(resourceRepository).deleteById(69L);
//    }
//
//    @Test
//    void deleteAll() {
//        resourceService.deleteAll();
//        Mockito.verify(resourceRepository).deleteAll();
//    }
}