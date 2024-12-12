package org.rocio.spring.bancors.controllers;

import org.rocio.spring.bancors.models.entity.Movimiento;
import org.rocio.spring.bancors.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("reportes")
public class ReporteController {

    @Autowired
    ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Movimiento>> obtenerReportePorClienteFechas(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime  fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin
    ){

    List<Movimiento> reporte= reporteService.generarReporteByIdCliente(clienteId, fechaInicio, fechaFin);
    return ResponseEntity.ok(reporte);
    }

}
