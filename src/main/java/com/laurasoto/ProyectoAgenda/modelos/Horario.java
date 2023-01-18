package com.laurasoto.ProyectoAgenda.modelos;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="horarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Horario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long horaDisponible;

    private boolean estaDisponible = true;
    
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    public Date getFechaAsDate(){
        return new Date(this.horaDisponible);
    }

    public Boolean isCanceledByOwner(Long idUser){
        return Objects.equals(idUser, this.getServicio().getEmpresa().getUsuarioAdmin().getId());
    }

}
