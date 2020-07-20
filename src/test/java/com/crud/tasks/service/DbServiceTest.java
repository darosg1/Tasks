package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

@RunWith (SpringRunner.class)
public class DbServiceTest {
    @InjectMocks
    DbService dbService;
    @Mock
    TaskRepository taskRepository;

    @Test
    public void getAllTasksTest(){
        //Given
        Task task1 = new Task(1L,"name1", "description1");
        Task task2 = new Task(2L,"name2", "description3");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        when(taskRepository.findAll()).thenReturn(taskList);
        //When
        List<Task> list=dbService.getAllTasks();
        //Then
        Assert.assertEquals(2, list.size ());
        Assert.assertEquals(1L, list.get(0).getId().longValue());
        Assert.assertEquals(2L, list.get(1).getId().longValue());
    }

    @Test
    public void getTaskTest(){
        //Given
        Task task1 = new Task(1L,"name1", "description1");
        when(taskRepository.findById(task1.getId())).thenReturn(Optional.ofNullable(task1));
        //When
        Optional<Task> fetchedTask=dbService.getTask(task1.getId());
        //Then
        Assert.assertNotNull(fetchedTask);
    }

    @Test
    public void saveTaskTest(){
        //Given
        Task task = new Task(1L,"name1", "description1");
        when(taskRepository.save(task)).thenReturn(task);
        //When
        Task savedTask = dbService.saveTask(task);
        //Then
        Assert.assertEquals(task, savedTask);
        Assert.assertEquals(1L, savedTask.getId().longValue ());
        Assert.assertEquals("name1", savedTask.getTitle());
        Assert.assertEquals("description1", savedTask.getContent());
    }

    @Test
    public void deleteTaskTest(){
        //Given
        Task task = new Task(1L,"name1", "description1");
        when(taskRepository.save(task)).thenReturn(task);
        //When
        dbService.saveTask(task);
        dbService.deleteTask(task.getId());
        //Then
        Assert.assertNotEquals (1, dbService.getTask(task.getId()));
    }
}