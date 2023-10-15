package br.com.kathulynneves.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

 /*
     * ID
     * Usuário(ID_USUARIO)
     * Descrição
     * Títilo
     * Data de início
     * Data de término
     * Prioridade
     */



@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

     @CreationTimestamp
     private LocalDateTime createdAt;


     public void setTitle(String title) throws Exception{//Passando a responsabilidade para quem está chamando o setTitle
        if(title.length() > 50){
            throw new Exception("O campo title deve conter no maximo 50 caracteres"); // exceção tratavel -- A mais generica que tem aqui
            //Toda vez que laçar uma exceção é necessario definir um tratamento para ela - é possivel tratar dentro do setTitle ou Deixa a responsabilidade de tratar para quem está chamando o setTitle
        }
        this.title = title;
     }
}