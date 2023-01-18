package com.laurasoto.ProyectoAgenda.servicios;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.laurasoto.ProyectoAgenda.modelos.Usuario;
import com.laurasoto.ProyectoAgenda.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio extends BaseServicio<Usuario> {
	private final UsuarioRepositorio usuarioRepositorio;

	private UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
		super(usuarioRepositorio);
		this.usuarioRepositorio = usuarioRepositorio;
	}

	public Usuario registerUser(Usuario usuario) {
		String hashed = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
		usuario.setPassword(hashed);
		return usuarioRepositorio.saveAndFlush(usuario);
	}

	public Usuario findByEmail(String email) {
		return usuarioRepositorio.findByEmail(email);
	}

	public Usuario findUserById(Long id) {
		Optional<Usuario> usuario = usuarioRepositorio.findById(id);
		if(usuario.isPresent()) {
            return usuario.get();
		} else {
			return null;
		}
    }

	public boolean authenticateUser(String email, String password) {
		Usuario usuario = usuarioRepositorio.findByEmail(email);
		// si no lo podemos encontrar por su email, retornamos false
		if (usuario == null) {
			return false;
		} else {
			// si el password coincide devolvemos true, sino, devolvemos false
			if (BCrypt.checkpw(password, usuario.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public List<ObjectError> updateUser(Usuario userForm){
		List<ObjectError> erroresValid = new ArrayList<>();
		Usuario user = findUserById(userForm.getId());

		if(!userForm.getPassword().isEmpty()){
			if(userForm.getPassword().equals(userForm.getPasswordConfirmation()) && 5 < userForm.getPassword().length()){
				String hashedPass = BCrypt.hashpw(userForm.getPassword(), BCrypt.gensalt());
				user.setPassword(hashedPass);
			} else {
				erroresValid.add(new ObjectError("password", "Por favor ingrese una contraseña segura"));
			}
		}

		if(!userForm.getNombre().isEmpty()){
			if(2 < userForm.getNombre().length() && userForm.getNombre().length() < 21){
				if(!userForm.getNombre().equals(user.getNombre())){
					String nomFormat = userForm.getNombre().substring(0,1).toUpperCase()+userForm.getNombre().substring(1).toLowerCase();
					user.setNombre(nomFormat);
				}
			} else {
				erroresValid.add(new ObjectError("nombre","Su nombre debe tener entre 3 y 20 caracteres"));
			}
		}

		if(!userForm.getApellido().isEmpty()){
			if(2 < userForm.getApellido().length() && userForm.getApellido().length() < 21){
				if(!userForm.getApellido().equals(user.getApellido())){
					String apFormat = userForm.getApellido().substring(0, 1).toUpperCase()+userForm.getApellido().substring(1).toLowerCase();
					user.setApellido(apFormat);
				}
			} else {
				erroresValid.add(new ObjectError("apellido","Su apellido debe tener entre 3 y 20 caracteres"));
			}
		}

		if(!userForm.getEmail().isEmpty()){
			if(validarEmail(userForm.getEmail()) && 9 < userForm.getEmail().length() && userForm.getEmail().length() < 31){
				if(!userForm.getEmail().equals(user.getEmail())){
					user.setEmail(userForm.getEmail());
				}
			} else {
				erroresValid.add(new ObjectError("email", "Error en formato del correo o largo (Min 10 y Max 30 Caracteres) "));
			}
		}	

		if(!user.equals(userForm)){
			usuarioRepositorio.save(user);
		}
		return erroresValid;
	}

	public boolean validarEmail(String email){
		String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return Pattern.compile(regexPattern).matcher(email).matches();
	}
}
