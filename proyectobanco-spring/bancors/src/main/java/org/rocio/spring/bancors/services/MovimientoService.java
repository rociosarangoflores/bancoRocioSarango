package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoService {

    List<Movimiento> listarMovimientos();
    Optional<Movimiento> guardarMovimiento(Long idCuenta, Movimiento movimiento);
    void eliminarMovimiento(Long id);
    List<Movimiento> buscarPorCliente(Long idCliente);

}
