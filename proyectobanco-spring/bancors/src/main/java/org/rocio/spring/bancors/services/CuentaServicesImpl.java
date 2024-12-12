package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Cliente;
import org.rocio.spring.bancors.models.entity.Cuenta;
import org.rocio.spring.bancors.models.entity.CuentaCliente;
import org.rocio.spring.bancors.repositories.ClienteRepository;
import org.rocio.spring.bancors.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaServicesImpl implements CuentaServices{

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listarCuentas() {
        return (List<Cuenta>) cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuenta> buscarCuentaById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    @Transactional
    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void eliminarCuenta(Long id) {
         cuentaRepository.deleteById(id);
    }

    @Override
    public Optional<Cuenta> buscarCuentaByIdCliente(Long idCliente) {
        Optional<Cuenta> o= cuentaRepository.findById(idCliente);
        if(o.isPresent()){
            Cuenta cuenta= o.get();
            if(!cuenta.getCuentaClientes().isEmpty()){
                List<Long> ids= cuenta.getCuentaClientes().stream().map(cu->cu.getClienteId())
                        .collect(Collectors.toList());
                List<Cliente>clientes= (List<Cliente>)clienteRepository.findAllById(ids);
                cuenta.setClientes(clientes);
            }
            return  Optional.of(cuenta);
        }
        return Optional.empty();
    }

    @Override
    public void eliminarCuentaByIdCliente(Long idCliente) {
        cuentaRepository.eliminarCuentaClientePorId(idCliente);

    }

    @Override
    public Optional<Cliente> asignarCuentaCliente(Cliente cliente, Long cuentId) {
        Optional<Cuenta> o= cuentaRepository.findById(cuentId);
        if (o.isPresent()){
            Optional<Cliente> clienteDet = clienteRepository.findById(cliente.getId());

            Cuenta cuenta = o.get();
            CuentaCliente cuentaCliente = new CuentaCliente();
            cuentaCliente.setClienteId(clienteDet.get().getId());

            cuenta.addCuentaCliente(cuentaCliente);
            cuentaRepository.save(cuenta);

            return  clienteDet;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cliente> crearClienteCuenta(Cliente cliente, Long cuentaId) {
        Optional<Cuenta> o= cuentaRepository.findById(cuentaId);
        if (o.isPresent()){
            Cliente clienteNuevo = clienteRepository.save(cliente);

            Cuenta cuenta = o.get();
            CuentaCliente cuentaCliente = new CuentaCliente();
            cuentaCliente.setClienteId(clienteNuevo.getId());

            cuenta.addCuentaCliente(cuentaCliente);
            cuentaRepository.save(cuenta);

            return  Optional.of(clienteNuevo);
        }


        return Optional.empty();
    }

    @Override
    public Optional<Cliente> eliminarClienteCuenta(Cliente cliente, Long cuentaId) {
        Optional<Cuenta> o= cuentaRepository.findById(cuentaId);
        if (o.isPresent()){
            Cliente clienteDet = clienteRepository.findById(cliente.getId()).get();

            Cuenta cuenta = o.get();
            CuentaCliente cuentaCliente = new CuentaCliente();
            cuentaCliente.setClienteId(clienteDet.getId());

            cuenta.removeCuentaCliente(cuentaCliente);
            cuentaRepository.save(cuenta);

            return  Optional.of(clienteDet);
        }


        return Optional.empty();
    }
}
