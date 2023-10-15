package br.com.kathulynneves.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kathulynneves.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){ //HttpServletRequest request setta o id do usuário
    var idUser = request.getAttribute("idUser");
    taskModel.setIdUser((UUID) idUser); //UUID está convertendo  o request/ indicando que o request é do tipo UUID
    
    //Verificar se data de início/fim é menor que a data atual
    var currentDate = LocalDateTime.now();
    if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
       .body("A data de início/termino deve ser maior do que a data atual"); 
    }

    //Verificar se data de início é depois da data de termino
    if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
       .body("A data de início deve ser menor que a data de término"); 
    }

    var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    //Listar todas as tarefas
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }
   
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){
        var task = this.taskRepository.findById(id).orElse(null);
        
        //Verificando se tarefa existe
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Tarefa não encontrada! ");
        }
        
         //Apenas o usuário que criou a tarefa pode altera-la
        var idUser = request.getAttribute("idUser");
       
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Usuário não tem permição para alterar essa tarefa! ");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);

        return ResponseEntity.ok().body(taskUpdated);
    }
}