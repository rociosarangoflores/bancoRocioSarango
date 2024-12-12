package org.rocio.spring.bancors.repositories;

import org.rocio.spring.bancors.models.entity.Movimiento;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaIdAndFechaBetween(Long idCuenta, LocalDateTime fechaInicio, LocalDateTime  fechaFin);
}
