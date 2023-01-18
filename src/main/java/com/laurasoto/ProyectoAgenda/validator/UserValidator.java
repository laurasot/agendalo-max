package com.laurasoto.ProyectoAgenda.validator;



import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.laurasoto.ProyectoAgenda.modelos.Usuario;
@Component
public class UserValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario = (Usuario) target;
        
        if (!usuario.getPasswordConfirmation().equals(usuario.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }         
    }
}
