package com.laurasoto.ProyectoAgenda.modelos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="servicios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicio{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @NotBlank
	private String servicioOfrecido;
	@Builder.Default
	private int duracionServicio=0;
	@Builder.Default
	private int horaInicio=0;
	@Builder.Default
	private int horaTermino=0;
	private int duracionJornada;
	
	private String direction;

	private String precio;

	private String description;

	private String imgRoute;

	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	

	@OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY)
	private List<Horario> horarios;

	/* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad; */
	
	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public void setHorarios(Horario horario){
		horarios.add(horario);
	}
	
	public List<List<TachamientoBoton>> posiblesHoraDisponible() throws ParseException{

		int horaInicio = this.getHoraInicio();
		int horarTermino = this.getHoraTermino();
		int cuantoDuraHora = this.getDuracionServicio(); //minutos

		int duracionJornada = horarTermino - horaInicio;
		this.setDuracionJornada(duracionJornada);

		int cantidadHorasDisponiblesDia = (duracionJornada * 60)/ cuantoDuraHora;

		//String dt = "2023-03-28";  // toma fecha en formato fecha
		String dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //creando formato
		Calendar calendario = Calendar.getInstance(); //crea calendario
		calendario.setTime(sdf.parse(dt));
		dt = sdf.format(calendario.getTime());  // obtengo tiempo

		//estoy haciendo una lista de fechas tipo date
		List<Date> posiblesHorariosDia = new ArrayList<>();
		//estoy haciendo los 7 dias, cada dia de los 7 tiene solo una hora, las 00, por lo 
		for (int i = 1; i < 8; i++) {
			posiblesHorariosDia.add(calendario.getTime());
			calendario.add(Calendar.DATE, 1);

		}

		//seteando en cada dia de la lista de posibles horarios dia, la hora de inicio
		List<Date> posiblesHorarios = new ArrayList<>();
		for (Date fechaDia : posiblesHorariosDia){
				Calendar diaFechaInicio = Calendar.getInstance(); // creates calendar
				diaFechaInicio.setTime(fechaDia);               // seteo la fecha que estan en lista
				diaFechaInicio.add(Calendar.HOUR_OF_DAY, horaInicio);      // adds one hour
				diaFechaInicio.getTime();
				//tengo mi fecha con la hora de inicio por los 7 dias siguientes
				posiblesHorarios.add(diaFechaInicio.getTime());
		}
		//estoy agregando las 12 horas en cada dia de la semana, queda una lista de lista de doce 12 horas
		List<List<Date>> listaDefinitiva = new ArrayList<>();
		List<Date> posiblesHorarios333 = new ArrayList<>();
		for (Date fechaDia : posiblesHorarios){
			List<Date> posiblesHorariosoooo = new ArrayList<>();
			Calendar diaFechaInicio = Calendar.getInstance(); // creates calendar
			diaFechaInicio.setTime(fechaDia); // seteo la fecha que estan en lista
			for (int i = 0; i < cantidadHorasDisponiblesDia; i++) {
				if(i == 0){
					posiblesHorarios333.add(diaFechaInicio.getTime());
					posiblesHorariosoooo.add(diaFechaInicio.getTime());
				}
				diaFechaInicio.add(Calendar.MINUTE, cuantoDuraHora); // adds one hour
				diaFechaInicio.getTime();
				posiblesHorarios333.add(diaFechaInicio.getTime());
				posiblesHorariosoooo.add(diaFechaInicio.getTime());

			}
			listaDefinitiva.add(posiblesHorariosoooo);
		}

		List<List<TachamientoBoton>> tachamientoBoton = new ArrayList<>();
		for (List<Date> dates : listaDefinitiva) {
			List<TachamientoBoton> botons = dates.stream()
					.map(date -> new TachamientoBoton(true,0, date,null))
					.collect(Collectors.toList());
			tachamientoBoton.add(botons);
		}
		//lo esta transformando de un objeto horario a long
		List<Long> allDateAsLong = this.horarios
				.stream()
				.map(Horario::getHoraDisponible)
				//el colect agrupa en una lista
				.collect(Collectors.toList());

		for (List<TachamientoBoton> tachamientoBotons : tachamientoBoton) {
			for (TachamientoBoton boton : tachamientoBotons) {
				if (allDateAsLong.contains(boton.getDate().getTime())){
					boton.setEstaActivo(false);
				}
			}
		}

		for (List<TachamientoBoton> tachamientoBotons : tachamientoBoton) {
			for (TachamientoBoton boton : tachamientoBotons) {
				for (Horario horario : this.horarios) {
					//1 = Duenho
					//2 = Cliente
					//0 = nadie

					if(horario.getHoraDisponible().equals(boton.getDate().getTime()) && horario.isCanceledByOwner(horario.getUsuario().getId())){
						boton.setHoraAgendadaByCliente(1);
						break;
					}
					else if (horario.getHoraDisponible().equals(boton.getDate().getTime()) && !horario.isCanceledByOwner(horario.getUsuario().getId())){
						boton.setHoraAgendadaByCliente(2);
						boton.setUsuario(horario.getUsuario());
						break;
					}
					else{
						boton.setHoraAgendadaByCliente(0);
					}
				}
			}
		}

		return tachamientoBoton;
	}
}
