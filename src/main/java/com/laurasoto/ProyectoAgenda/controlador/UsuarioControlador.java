package com.laurasoto.ProyectoAgenda.controlador;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.laurasoto.ProyectoAgenda.modelos.Region;
import com.laurasoto.ProyectoAgenda.modelos.Usuario;
import com.laurasoto.ProyectoAgenda.servicios.EmpresaServicio;
import com.laurasoto.ProyectoAgenda.servicios.RegionServicio;
import com.laurasoto.ProyectoAgenda.servicios.UsuarioServicio;
import com.laurasoto.ProyectoAgenda.utiles.Funciones;
import com.laurasoto.ProyectoAgenda.validator.UserValidator;

@Controller
@SuppressWarnings("unused")
public class UsuarioControlador {
	private final UsuarioServicio usuarioServicio;
	private final EmpresaServicio empresaServicio;
	private final RegionServicio regionServicio;
	private final UserValidator userValidator;
	// private final Servicio1Servicio servicio1Servicio;

	public UsuarioControlador(UsuarioServicio usuarioServicio, EmpresaServicio empresaServicio, UserValidator userValidator, RegionServicio regionServicio) {
		this.usuarioServicio = usuarioServicio;
		this.empresaServicio = empresaServicio;
		this.userValidator = userValidator;
		this.regionServicio = regionServicio;
	}
	// private final Servicio1Servicio servicio1Servicio;


	@GetMapping("/registration")
	public String muestraForm(@ModelAttribute("usuario") Usuario usuario, Model model) {
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		//List<Ciudad> ciudades = CiudadServicio.ciudadesMostrar(empresa);

		model.addAttribute("regiones", regiones);
		model.addAttribute("regionesJson", resultadoJson);

		return "creaUsuario";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, HttpSession session, Model model) {
		userValidator.validate(usuario, result);
		if (result.hasErrors()) {
			return "creaUsuario";
		}
		int tipoUsuario = 0;
		if(usuarioServicio.traerTodo().size() == 0){
			tipoUsuario = 500;
		}
		if(usuarioServicio.findByEmail(usuario.getEmail()) == null){
			usuario.setTipoUsuario(tipoUsuario);
			Usuario usuarioNuevo = usuarioServicio.registerUser(usuario);
			session.setAttribute("usuarioId",usuarioNuevo.getId());
			return "redirect:/";
		}
		model.addAttribute("error", "ya tienes una cuenta con ese email");
		
		return "creaUsuario";
	}

	@GetMapping("/login")
	public String login(HttpSession session, Model model) {
		if ((Long) session.getAttribute("usuarioId") != null) {
			return "redirect:/";
		}

		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		//List<Ciudad> ciudades = CiudadServicio.ciudadesMostrar(empresa);

		model.addAttribute("regiones", regiones);
		model.addAttribute("regionesJson", resultadoJson);
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model, HttpSession session) {
		boolean autenticacion = usuarioServicio.authenticateUser(email, password);
		if (autenticacion) {
			// si es verdadero
			Usuario usuario = usuarioServicio.findByEmail(email);
			session.setAttribute("usuarioId", usuario.getId());
			return "redirect:/";
		} else { // si es falso
			model.addAttribute("error", "Credencial invalida, intentelo de nuevo por favor");
			return "login";
		}
	}
	@GetMapping("/")
	public String home(HttpSession session, Model model){

		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);

		if((Long) session.getAttribute("usuarioId") != null ){
			Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
			model.addAttribute("usuario", usuario);
		}
		
		
		model.addAttribute("regiones", regiones);
		model.addAttribute("regionesJson", resultadoJson);
		return"index";
	}

	@GetMapping("/logout")
	public String cierraSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/perfil/{idUser}")
	public String perfilUsuario(HttpSession session, @PathVariable("idUser") Long idUser, @ModelAttribute("userForm") Usuario userForm, Model model){
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		Usuario user = usuarioServicio.findById(idUser);

		model.addAttribute("regiones", regiones);
		model.addAttribute("regionesJson", resultadoJson);
		model.addAttribute("usuario", user);
		return "editarUsuario";
	}

	@PostMapping("/perfil/actualizar")
	public String editarUser(HttpSession session, @ModelAttribute("userForm") Usuario userForm, Model model, BindingResult result){
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		List<ObjectError> listErrors = usuarioServicio.updateUser(userForm);

		if(!listErrors.isEmpty()){
			for (ObjectError error : listErrors) {
				result.addError(error);
			}
			Usuario user = usuarioServicio.findById(userForm.getId());

			model.addAttribute("regiones", regiones);
			model.addAttribute("regionesJson", resultadoJson);
			model.addAttribute("usuario", user);
			return "editarUsuario";
		} 
		return "redirect:/";
	}
}
