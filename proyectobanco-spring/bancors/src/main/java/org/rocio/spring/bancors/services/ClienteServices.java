package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteServices {
    Optional<Cliente> crearCliente(Cliente cliente);
    Optional<Cliente> buscarClientePorId(Long id);
    Optional<Cliente> modificarCliente(Cliente cliente);
    void eliminarClientePorId (Long id);
    List<Cliente> listarClientes();

    List<Cliente> listarClientePorIdsCuenta(Iterable<Long> ids);
}
