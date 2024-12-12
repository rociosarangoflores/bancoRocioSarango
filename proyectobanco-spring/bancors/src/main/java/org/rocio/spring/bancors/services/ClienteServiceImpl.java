package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Cliente;
import org.rocio.spring.bancors.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteServices {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Optional<Cliente> crearCliente(Cliente cliente) {
       /* Optional<Cliente> clienteOpt= clienteRepository.findById(cliente.getId());
        if(clienteOpt.isPresent()){
            return Optional.empty();
        }
        else {*/
            Cliente clienteNuevo= clienteRepository.save(cliente);
            return Optional.of(clienteNuevo);
        //}

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Cliente> modificarCliente(Cliente cliente) {
        Optional<Cliente> clienteOpt= clienteRepository.findById(cliente.getId());
        if(clienteOpt.isPresent()){
            Cliente clienteModificado= clienteRepository.save(cliente);
            return Optional.of(clienteModificado);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarClientePorId(Long id) {
       clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return (List<Cliente>)clienteRepository.findAll();
    }

    @Override
    public List<Cliente> listarClientePorIdsCuenta(Iterable<Long> ids) {
        return (List<Cliente>) clienteRepository.findAllById(ids);
    }
}
