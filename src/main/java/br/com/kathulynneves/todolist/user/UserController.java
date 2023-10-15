package br.com.kathulynneves.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/*
 * Modificadores:
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    /*
     * String(texto)
     * Integer (int) numeros inteiros
     * Double (double) Números 0.0000
     * Float (float) Números 0.000
     * Char (A C)
     * Date (data)
     * void
     */

     /*
      * Body
      */
    
    @Autowired
    private IUserrepository userrepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){ //ResquesBody informar que essa requisição entá dentro do body -- Significa que oq está recebendo pelo create vem dentro do body

        //ResponseEntity permite passar para o return tanto algo deu certo, como algo que deu erro
        var user = this.userrepository.findByUsername(userModel.getUsername());

        if(user != null){
            //Mesangem de erro
            //Status Code 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = this.userrepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
