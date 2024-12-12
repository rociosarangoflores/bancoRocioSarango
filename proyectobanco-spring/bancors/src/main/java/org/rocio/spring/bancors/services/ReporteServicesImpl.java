package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Cuenta;
import org.rocio.spring.bancors.models.entity.Movimiento;
import org.rocio.spring.bancors.repositories.CuentaRepository;
import org.rocio.spring.bancors.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReporteServicesImpl implements ReporteService {

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    MovimientoRepository movimientoRepository;

    @Override
    public List<Movimiento> generarReporteByIdCliente(Long idCliente, LocalDateTime  fechaIncio, LocalDateTime fechaFin) {
        Iterable<Long> ids =  new HashSet<>();
        ((Set<Long>) ids).add(idCliente);
        List<Cuenta> listaCuentas = (List<Cuenta>) cuentaRepository.findAllById(ids);
        List<Movimiento> listaMovimiento = new ArrayList<>();

        for(Cuenta cuenta: listaCuentas){
            listaMovimiento = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), fechaIncio, fechaFin);
        }
        return listaMovimiento.stream()
                .sorted((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()))
                .collect(Collectors.toList());
    //}
       // return listaMovimiento;
    }
}
