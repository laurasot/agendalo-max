package com.laurasoto.ProyectoAgenda.modelos;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@NotBlank
	@Size(min = 3, max = 20, message = "Su nombre debe tener entre 3 y 20 caracteres")
	private String nombre;
	@NotNull
	@NotBlank
	@Size(min = 3, max = 20, message = "Su apellido debe tener entre 3 y 20 caracteres")
	private String apellido;
	private Integer tipoUsuario;
	@NotNull(message = "Debes agregar un número de celular")
	private Integer numCelular;
	@NotNull @NotBlank
	@Size(min = 11, max = 12, message = "Ingrese un rut válido")
	private String rut;
	@Email(message = "Formato de correo electronico incorrecto, ejemplo: agendalo@user.com")
	@Size(min = 10, max = 30, message = "Su correo debe tener entre 10 y 30 caracteres")
	private String email;
	
	@NotBlank(message = "Por favor no deje su contraseña en blanco")
	@Size(min = 6, message = "Por favor ingrese una contraseña segura")
	private String password;
	@Transient
	private String passwordConfirmation;
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;

	@OneToOne(mappedBy = "usuarioAdmin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Empresa empresa;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Horario> horarios;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
