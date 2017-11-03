package com.droux.jtshirt.controller;

import com.droux.jtshirt.controller.storage.FileSystemStorageService;
import com.droux.jtshirt.controller.storage.StorageFileNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.when;


public class FilteRepoTest {

    private FileSystemStorageService fileSystemStorageService;

    @Mock
    private Environment env;

    @Test(expected = StorageFileNotFoundException.class)
    public void rootFolderNotFound() {
        fileSystemStorageService.loadAsResource("default.jpg");
    }


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(env.getProperty("storage.location")).thenReturn("C:\\Users\\Fumble\\IdeaProjects\\imgaaa");
        fileSystemStorageService = new FileSystemStorageService(env);
    }
}
