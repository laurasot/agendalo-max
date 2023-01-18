package com.laurasoto.ProyectoAgenda.modelos;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull @NotBlank
	@Size(min=3, max=20, message="El nombre debe tener entre 3 y 20 caracteres")
	@Column(unique = true)
	private String nombre;
	@NotNull @NotBlank
	@Size(min = 9, max = 10, message = "Ingrese un rut válido")
	private String rut;

	private boolean empresafree = true;

	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioadmin_id")
	private Usuario usuarioAdmin;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
	private List<Servicio> servicios;

	public void setServicios(Servicio servicio){
		servicios.add(servicio);
	}

	public void setServicios(List<Servicio> servicios){ this.servicios = servicios;}
}
