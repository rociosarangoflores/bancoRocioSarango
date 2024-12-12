package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Movimiento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReporteService {

   List<Movimiento> generarReporteByIdCliente(Long idCliente, LocalDateTime fechaIncio, LocalDateTime  fechaFin);

}
