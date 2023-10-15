package br.com.kathulynneves.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


// class possui métodos, atributos e os metodos possuem uma implementação 
// interface é apenas uma representação dos métodos - possui metodos mas não suas implementações
public interface IUserrepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
}
