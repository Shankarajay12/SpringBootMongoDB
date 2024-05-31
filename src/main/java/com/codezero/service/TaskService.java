package com.codezero.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codezero.model.Task;
import com.codezero.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    //CRUD  CREATE , READ , UPDATE , DELETE


    public Task addTask(Task task) {
    	String[] str=UUID.randomUUID().toString().split("-");
        StringBuffer sb= new StringBuffer();
        for(int i=0;i<str.length;i++)
        {
            sb.append(str[i]);
        }
        
        task.setTaskId(sb.substring(0,24).toString());
        
        return repository.save(task);
    }

    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    public Task getTaskByTaskId(String taskId){
        return repository.findById(taskId).get();
    }

    public List<Task> getTaskBySeverity(int severity){
        return  repository.findBySeverity(severity);
    }

    public List<Task> getTaskByAssignee(String assignee){
        return repository.getTasksByAssignee(assignee);
    }

    public Task updateTask(Task taskRequest){
        //get the existing document from DB
        // populate new value from request to existing object/entity/document
        Task existingTask = repository.findById(taskRequest.getTaskId()).get();
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setSeverity(taskRequest.getSeverity());
        existingTask.setAssignee(taskRequest.getAssignee());
        existingTask.setStoryPoint(taskRequest.getStoryPoint());
        return repository.save(existingTask);
    }

    public String deleteTask(String taskId){
        repository.deleteById(taskId);
        return taskId+" task deleted from dashboard ";
    }
}