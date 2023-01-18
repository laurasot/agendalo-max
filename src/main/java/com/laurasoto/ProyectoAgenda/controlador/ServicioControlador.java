package com.laurasoto.ProyectoAgenda.controlador;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.laurasoto.ProyectoAgenda.modelos.Ciudad;
import com.laurasoto.ProyectoAgenda.modelos.Empresa;
import com.laurasoto.ProyectoAgenda.modelos.Horario;
import com.laurasoto.ProyectoAgenda.modelos.Region;
import com.laurasoto.ProyectoAgenda.modelos.Servicio;
import com.laurasoto.ProyectoAgenda.modelos.TachamientoBoton;
import com.laurasoto.ProyectoAgenda.modelos.Usuario;
import com.laurasoto.ProyectoAgenda.servicios.CiudadServicio;
import com.laurasoto.ProyectoAgenda.servicios.EmpresaServicio;
import com.laurasoto.ProyectoAgenda.servicios.HorarioServicio;
import com.laurasoto.ProyectoAgenda.servicios.RegionServicio;
import com.laurasoto.ProyectoAgenda.servicios.Servicio1Servicio;
import com.laurasoto.ProyectoAgenda.servicios.UsuarioServicio;
import com.laurasoto.ProyectoAgenda.utiles.Funciones;

@Controller
@SuppressWarnings("unused")
public class ServicioControlador {
    private final UsuarioServicio usuarioServicio;
    private final EmpresaServicio empresaServicio;
	private final Servicio1Servicio servicio1Servicio;
	private final CiudadServicio ciudadServicio;
	private final RegionServicio regionServicio;
    private final HorarioServicio horarioServicio;
	
	public ServicioControlador(EmpresaServicio empresaServicio, Servicio1Servicio servicio1Servicio,
			UsuarioServicio usuarioServicio, CiudadServicio ciudadServicio, RegionServicio regionServicio, HorarioServicio horarioServicio){
		this.empresaServicio = empresaServicio;
		this.servicio1Servicio = servicio1Servicio;
		this.usuarioServicio = usuarioServicio;
		this.ciudadServicio = ciudadServicio;
		this.regionServicio = regionServicio;
        this.horarioServicio = horarioServicio;
	}

    @GetMapping("empresa/horario/{empresaId}/{servicioId}")
    public String crearHoraDisponible(@ModelAttribute("horario")Horario horario,  HttpSession session, Model model, 
    @PathVariable("empresaId") Long empresaId, @PathVariable("servicioId") Long servicioId) throws ParseException{
        if((Long) session.getAttribute("usuarioId") == null){
			return"redirect:/";
		}
        List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
        Empresa empresa = empresaServicio.findById(empresaId);
        Servicio servicio = servicio1Servicio.findById(servicioId);
        List<Ciudad> ciudades = ciudadServicio.ciudadesMostrar(empresa);
        List<List<TachamientoBoton>> listaAlModel = servicio.posiblesHoraDisponible();
        Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId") );

        
        servicio1Servicio.crear(servicio);
    
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresa", empresa);
        model.addAttribute("servicio", servicio);
        model.addAttribute("regiones", regiones);
		model.addAttribute("ciudades", ciudades);
        model.addAttribute("regionesJson", resultadoJson);
        model.addAttribute("listaAlModel", listaAlModel);
        return"horaDisponible";
    }
    
    
    @GetMapping("/agendar/{servicioId}/{horaLong}")
        public String horaNoDisponible(HttpSession session, @PathVariable("horaLong") Long horaLong, 
        @PathVariable("servicioId") Long servicioId){
            //misma funcion para usuario y para empresa 
            Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
            Horario horario = new Horario();
            horario.setHoraDisponible(horaLong);
            horario.setUsuario(usuario);
            horario.setId(horaLong);
            horario.setServicio(servicio1Servicio.findById(servicioId));
            horarioServicio.crear(horario);
            return"redirect:/empresa/horario/" + usuario.getEmpresa().getId() + "/{servicioId}";

        }

    @GetMapping("/agendar/disponible/{servicioId}/{horaLong}")
    public String horaDisponible(HttpSession session, @PathVariable("servicioId") Long servicioId, @PathVariable("horaLong") Long horaLong){
        Usuario usuario = usuarioServicio.findById((Long) session.getAttribute("usuarioId"));
        Horario horario = horarioServicio.findByHoraDisponible(horaLong);
        horarioServicio.delete(horario.getId());
        return"redirect:/empresa/horario/"+ usuario.getEmpresa().getId() + "/{servicioId}";
    }

    @GetMapping("/agendamiento/{servicioId}/{horaLong}")
    public String agendarHora(@PathVariable("servicioId") Long servicioId, @PathVariable("horaLong") Long horaLong, Model model, HttpSession session){
        if( (Long) session.getAttribute("usuarioId") == null ){
            model.addAttribute("loguearseParaAgendar", "Debes Loguearte para agendar");
            return"redirect:/";
        }

        Servicio servicio = servicio1Servicio.findById(servicioId);
        Usuario usuario = usuarioServicio.findById ((Long) session.getAttribute("usuarioId"));
        Horario horario = new Horario();
        horario.setUsuario(usuario);
        horario.setHoraDisponible(horaLong);
        horario.setServicio(servicio);
        horarioServicio.crear(horario);

        return "redirect:/horas/usuario/" + usuario.getId();
    }

    @GetMapping("/horas/usuario/{usuarioId}")
    public String horaAgendadasUsuario(HttpSession session, @PathVariable("usuarioId") Long usuarioId, Model model){
        List<Region> regiones = regionServicio.regionesTodas();
		String resultadoJson = new Funciones().regionesToJson(regiones);
        Usuario usuario = usuarioServicio.findById ((Long) session.getAttribute("usuarioId"));

        model.addAttribute("regiones", regiones);
        model.addAttribute("regionesJson", resultadoJson);
        model.addAttribute("usuario", usuario);

        return"agendamientos";
    }

    @GetMapping("/cancela/cita/{usuarioId}/{horarioId}")
    public String eliminarCita(HttpSession session, @PathVariable("usuarioId") Long usuarioId, @PathVariable("horarioId") Long horarioId){
        Usuario usuario = usuarioServicio.findById ((Long) session.getAttribute("usuarioId"));
        horarioServicio.delete(horarioId);
        return"redirect:/horas/usuario/{usuarioId}";
    }
}
