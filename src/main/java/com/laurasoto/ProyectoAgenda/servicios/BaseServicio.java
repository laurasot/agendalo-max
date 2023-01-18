package com.laurasoto.ProyectoAgenda.servicios;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.laurasoto.ProyectoAgenda.repositorios.BaseRepositorio;

@Service
public abstract class BaseServicio<T> {
	BaseRepositorio<T> baseRepositorio;

	public BaseServicio(BaseRepositorio<T> baseRepositorio) {
		this.baseRepositorio = baseRepositorio;
	}

	public List<T> traerTodo() {
		return baseRepositorio.findAll();
	}

	public T crear(T objeto) {
		return baseRepositorio.save(objeto);
	}

	public T findById(Long id) {
		Optional<T> u = baseRepositorio.findById(id);
		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		baseRepositorio.deleteById(id);
	}
}
