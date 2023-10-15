package br.com.kathulynneves.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.kathulynneves.todolist.user.IUserrepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class filterTaskAuth extends OncePerRequestFilter{

    @Autowired

    private IUserrepository userrepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();

                if(servletPath.startsWith(("/tasks/"))){

                     // Pegar a autenticação (usuário e senha)
                    var authorizations = request.getHeader("Authorization");

                    var authEncoded =   authorizations.substring("Basic".length()).trim();
                    
                    byte[] authDecode = Base64.getDecoder().decode(authEncoded);

                    var authString = new String(authDecode);

                    Base64.getDecoder().decode(authEncoded);
                    
                    

                    String[] credentials = authString.split(":");
                    String username = credentials[0];
                    String password = credentials[1];

                    System.out.println("Autorizatioon");
                    System.out.println(username);
                    System.out.println(password);

                    //Validar usuário

                    var user = this.userrepository.findByUsername(username);
                    if(user == null){
                        response.sendError(401);
                    }else{
                        //Validar senha
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                        if(passwordVerify.verified){
                            //Segue viagem
                            //Enviando para a controller -- passa nome do atributo e qual o valor
                            request.setAttribute("idUser", user.getId()); 
                            filterChain.doFilter(request, response);//resquest é tudo oq está vindo da nossa requisição e response é tudo oq estamos enviando par o usuário
                        }else{
                            response.sendError(401);
                        }
                        }
                        

        
                    }else{
                        filterChain.doFilter(request, response);
                    }
    
    }
    
    


}
        
               