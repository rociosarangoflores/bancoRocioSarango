package org.rocio.spring.bancors.repositories;


import org.rocio.spring.bancors.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
