package br.com.kathulynneves.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
                                                    //Entidade --- ID/Chave primaria da entidade
import java.util.List;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
}