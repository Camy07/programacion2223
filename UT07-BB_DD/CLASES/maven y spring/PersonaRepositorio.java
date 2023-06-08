package es.ieslosalbares;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonaRepositorio extends CrudRepository<Persona , Long> {

    Persona findById(long Id);

    List<Persona> findByName(String name);

}
