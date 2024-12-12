package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Cliente;
import org.rocio.spring.bancors.models.entity.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaServices {

    List<Cuenta> listarCuentas();
    Optional<Cuenta> buscarCuentaById(Long id);
    Cuenta guardarCuenta(Cuenta cuenta);
    void eliminarCuenta (Long id);
    Optional<Cuenta> buscarCuentaByIdCliente(Long idCliente);
    void eliminarCuentaByIdCliente(Long idCliente);

    Optional<Cliente> asignarCuentaCliente(Cliente cliente, Long cuentId);
    Optional<Cliente> crearClienteCuenta(Cliente cliente, Long cuentaId);
    Optional<Cliente> eliminarClienteCuenta (Cliente cliente, Long cuentaId);
}
