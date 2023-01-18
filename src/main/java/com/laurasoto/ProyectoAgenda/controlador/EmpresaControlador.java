package com.laurasoto.ProyectoAgenda.controlador;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.laurasoto.ProyectoAgenda.modelos.Ciudad;
import com.laurasoto.ProyectoAgenda.modelos.Empresa;
import com.laurasoto.ProyectoAgenda.modelos.Region;
import com.laurasoto.ProyectoAgenda.modelos.Servicio;
import com.laurasoto.ProyectoAgenda.modelos.Usuario;
import com.laurasoto.ProyectoAgenda.servicios.CiudadServicio;
import com.laurasoto.ProyectoAgenda.servicios.EmpresaServicio;
import com.laurasoto.ProyectoAgenda.servicios.RegionServicio;
import com.laurasoto.ProyectoAgenda.servicios.Servicio1Servicio;
import com.laurasoto.ProyectoAgenda.servicios.UsuarioServicio;
import com.laurasoto.ProyectoAgenda.utiles.Funciones;

@Controller
@SuppressWarnings("unused")
public class EmpresaControlador {
	private final EmpresaServicio empresaServicio;
	private final Servicio1Servicio servicio1Servicio;
	private final UsuarioServicio usuarioServicio;
	private final CiudadServicio ciudadServicio;
	private final RegionServicio regionServicio;
	
	public EmpresaControlador(EmpresaServicio empresaServicio, Servicio1Servicio servicio1Servicio,
			UsuarioServicio usuarioServicio, CiudadServicio ciudadServicio, RegionServicio regionServicio){
		this.empresaServicio = empresaServicio;
		this.servicio1Servicio = servicio1Servicio;
		this.usuarioServicio = usuarioServicio;
		this.ciudadServicio = ciudadServicio;
		this.regionServicio = regionServicio;
	}
	
	@GetMapping("/planes")
	public String elegirPlan(Model model, HttpSession session){

		if((Long) session.getAttribute("usuarioId") != null){
			Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
			model.addAttribute("usuario", usuario);
		}
		return "membresias";
	}
	
	@PostMapping("/search")
	public String buscaServicio(@RequestParam("selectReg") Long selectReg,@RequestParam("selectCiud") Long selectCiud,
	@RequestParam("servicio") String servicio){
		String serviceEncode = URLEncoder.encode(servicio, StandardCharsets.UTF_8);
        return "redirect:/search/"+selectReg+"/"+ selectCiud+"/"+ serviceEncode;
	}
	
	@GetMapping("/search/{regionId}/{ciudadId}/{servicio}")
	public String formServicio(@PathVariable("servicio") String servicio, @PathVariable("regionId") Long regionId, @PathVariable("ciudadId") Long ciudadId,
	HttpSession session, Model model){
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		//debo preguntar si id existe 
		if((Long) session.getAttribute("usuarioId") != null){
			Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
			model.addAttribute("usuario", usuario);
		}
		List<Servicio> servicioRequerido = servicio1Servicio.obtieneServicioPorServicioOfrecido(servicio);
		if(servicioRequerido.size() == 0){
			model.addAttribute("errorServicio", "No encontramos el servicio que estabas buscando");
			model.addAttribute("regionesJson", resultadoJson);
			model.addAttribute("regiones", regiones);
			return"servicio";
		}
		Ciudad ciudad = ciudadServicio.findById(ciudadId);
		//filtro por ciudad
		List<Empresa> empresasFiltroCiudad = empresaServicio.getEmpresaPorCiudad(ciudad);
		List<Servicio> servicios = new ArrayList<>();
		for (Empresa empresa : empresasFiltroCiudad) {
			servicios.addAll(empresa.getServicios());
		}
		List<Servicio> serviciosFiltradosPorNombreCiudad = servicios.stream()
				.filter(servicio1 -> servicio1.getServicioOfrecido().contains(servicio))
				.collect(Collectors.toList());

		model.addAttribute("serviciosFiltradosPorNombreCiudad", serviciosFiltradosPorNombreCiudad);
		if(serviciosFiltradosPorNombreCiudad.size() == 0){
			model.addAttribute("errorNoHayEmpresa", "Lo sentimos, en esa ciudad no se encuentra el servicio que buscas");
			model.addAttribute("regionesJson", resultadoJson);
			model.addAttribute("regiones", regiones);
		}
		model.addAttribute("regionesJson", resultadoJson);
		model.addAttribute("regiones", regiones);
		return"servicio";
	}
	//se puede tener dos empresas con el mismo nombre?
	@GetMapping("/planes/new")
	public String nuevoPlan(@ModelAttribute("empresa") Empresa empresa, HttpSession session, Model model){
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		if((Long) session.getAttribute("usuarioId") == null){
			return"redirect:/";
		}

		Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
		List<Ciudad> ciudades = ciudadServicio.ciudadesMostrar(empresa);
		
		model.addAttribute("regiones", regiones);
		model.addAttribute("ciudades", ciudades);
		model.addAttribute("usuario", usuario);
		model.addAttribute("regionesJson", resultadoJson);
		return"creaEmpresa";
	}
	//validacion en crear empresa, si elige premium puede tener mas de un servicio
	@PostMapping("/planes/new")
	public String formPlanFree(@Valid @ModelAttribute("empresa") Empresa empresa, BindingResult result, HttpSession session, Model model){
		if(result.hasErrors()){
			List<Region> regiones = regionServicio.regionesTodas();
			String resultadoJson = new Funciones().regionesToJson(regiones);
			Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
			List<Ciudad> ciudades = ciudadServicio.ciudadesMostrar(empresa);
		
			model.addAttribute("regiones", regiones);
			model.addAttribute("ciudades", ciudades);
			model.addAttribute("usuario", usuario);
			model.addAttribute("regionesJson", resultadoJson);
			return"creaEmpresa";
		}
		if(empresaServicio.getEmpresaPorNombre(empresa.getNombre()) != null){
			model.addAttribute("error", "no puedes usar ese nombre porque ya existe");
			return"creaEmpresa";
		}
		Usuario usuarioAdmin = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
			empresa.setUsuarioAdmin(usuarioAdmin);
			empresaServicio.crear(empresa);
			return"redirect:/plan/"+empresa.getId();
	}
	

	@GetMapping("/plan/{idEmpresa}")
	public String empresaDetalle(@ModelAttribute("servicio") Servicio servicio,@PathVariable("idEmpresa") Long idEmpresa, HttpSession session, Model model){
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		Empresa empresa = empresaServicio.findById(idEmpresa);
		if((Long) session.getAttribute("usuarioId") == null){
			return"redirect:/";
		}
		if((Long) session.getAttribute("usuarioId") != empresa.getUsuarioAdmin().getId()){
			System.out.println( "id usuario"+(Long) session.getAttribute("usuarioId"));
			System.out.println("id usuario dueño empresa:" + empresa.getUsuarioAdmin().getId());
			return"redirect:/";
		}
		Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));

		List<Ciudad> ciudades = ciudadServicio.ciudadesMostrar(empresa);
		List<Servicio> servicios = servicio1Servicio.traerTodo();
		//List<Servicio> serviciosNotEmpresa = servicio1Servicio.serviciosNoContieneEmpresa(empresa);
		List<Ciudad>  ciudadesNotEmpresa = ciudadServicio.ciudadesNoContieneEmpresa(empresa);

		
		model.addAttribute("ciudadesNotEmpresa", ciudadesNotEmpresa);
		//model.addAttribute("serviciosNotEmpresa", serviciosNotEmpresa);
		model.addAttribute("servicios", servicios);
		model.addAttribute("empresa", empresa);
		model.addAttribute("usuario", usuario);
		model.addAttribute("regiones", regiones);
		model.addAttribute("regionesJson", resultadoJson);
		model.addAttribute("ciudades", ciudades);
		return "showEmpresa";
	}

	@PostMapping("/plan/{idEmpresa}")
	public String crearServcicio(@Valid @ModelAttribute("servicio") Servicio servicio,BindingResult result ,
								HttpSession session, @PathVariable("idEmpresa") Long idEmpresa, @RequestParam("postFile") MultipartFile postFile){
		if(result.hasErrors()){
			return"showEmpresa";
		}
		if(postFile.isEmpty() == false){
			String fileName = "servicioPicture";
			String imgRoute = "/img/" + idEmpresa + "/" + servicio;
			File directory = new File("src/main/resources/static" + imgRoute);
			if(directory.exists() == false){
				directory.mkdirs();
			}
			try {
				byte[] bytes = postFile.getBytes();
				BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(
						new File(directory.getAbsolutePath() + "/" + fileName)
					)
				);
				outputStream.write(bytes);
				outputStream.flush();
				outputStream.close();
				System.out.println("El archivo se ha cargado con éxito.");
				servicio.setImgRoute(imgRoute + "/" + fileName);
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
				System.out.println("Ocurrió un error al cargar la imagen. " + e);
			}
		}
		Empresa empresa = empresaServicio.findById(idEmpresa);
		servicio.setEmpresa(empresa);
		servicio1Servicio.crear(servicio);
		return "redirect:/plan/"+ idEmpresa;
	}

	@PostMapping("plan/{idEmpresa}/edit")
	public String editaEmpresaForm(@Valid @ModelAttribute("empresa") Empresa empresaEditar, BindingResult result, @PathVariable("idEmpresa") Long idEmpresa,
	HttpSession session){
		if(result.hasErrors()){
			return"editaEmpresa";
		}

		Usuario usuarioAdmin = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
		empresaEditar.setId(idEmpresa);
		empresaEditar.setUsuarioAdmin(usuarioAdmin);
		empresaServicio.crear(empresaEditar);
		return"redirect:/plan/"+idEmpresa;
	}

	@GetMapping("plan/{idEmpresa}/edit")
	public String editaEmpresa(@ModelAttribute("empresa") Empresa empresa, @PathVariable("idEmpresa") Long idEmpresa, HttpSession session, Model model){
		if((Long) session.getAttribute("usuarioId") == null){
			return"redirect:/";
		}
		
		List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
		Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
		List<Ciudad> ciudades = ciudadServicio.traerTodo();
		Empresa empresaAEditar = empresaServicio.findById(idEmpresa);
		List<Ciudad>  ciudadesNotEmpresa = ciudadServicio.ciudadesNoContieneEmpresa(empresaAEditar);
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("regiones", regiones);
		model.addAttribute("empresaAEditar", empresaAEditar);
		model.addAttribute("ciudadesNotEmpresa", ciudadesNotEmpresa);
		model.addAttribute("regionesJson", resultadoJson);
		model.addAttribute("ciudades", ciudades);
		return "editaEmpresa";
	}



	@GetMapping("/delete/{idEmpresa}")
	public String eliminaEmpresa(HttpSession session, @PathVariable("idEmpresa") Long idEmpresa ){
		Empresa empresa = empresaServicio.findById(idEmpresa);
		if((Long) session.getAttribute("usuarioId") == null && (Long) session.getAttribute("usuarioId") != empresa.getUsuarioAdmin().getId()){
			return"redirect:/";
		}
		empresa.setServicios(new ArrayList<>());
		empresa.setUsuarioAdmin(null);
		empresaServicio.delete(idEmpresa);
		
		return"redirect:/";
	}

	@GetMapping("/premium/{idEmpresa}")
	public String cambiarAPremium(HttpSession session, @PathVariable("idEmpresa") Long idEmpresa){
		Empresa empresa = empresaServicio.findById(idEmpresa);
		if((Long) session.getAttribute("usuarioId") == null && (Long) session.getAttribute("usuarioId") != empresa.getUsuarioAdmin().getId()){
			return"redirect:/";
		}
		empresa.setEmpresafree(false);
		empresaServicio.crear(empresa);
		return"redirect:/plan/"+ idEmpresa;
	}

	@GetMapping("/delete/{idEmpresa}/{idServicio}")
	public String desconectaServicio(HttpSession session, @PathVariable("idEmpresa") Long idEmpresa, 
	@PathVariable("idServicio") Long idServicio){
		Empresa empresa = empresaServicio.findById(idEmpresa);
		List<Servicio> empresaServicios = empresa.getServicios();
		Servicio servicio = servicio1Servicio.findById(idServicio);
		servicio.setEmpresa(null);
		servicio1Servicio.crear(servicio);
		return"redirect:/plan/"+idEmpresa;
	}


	@GetMapping("/premium/delete/{empresaId}")
	public String cambiarAFree(HttpSession session, @PathVariable("empresaId") Long empresaId){
		Empresa empresa = empresaServicio.findById(empresaId);

		empresa.setEmpresafree(true);
		empresaServicio.crear(empresa);
		return"redirect:/plan/{empresaId}";
	}

}

