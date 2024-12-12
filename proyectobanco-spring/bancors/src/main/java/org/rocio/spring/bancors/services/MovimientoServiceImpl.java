package org.rocio.spring.bancors.services;

import org.rocio.spring.bancors.models.entity.Cuenta;
import org.rocio.spring.bancors.models.entity.Movimiento;
import org.rocio.spring.bancors.repositories.CuentaRepository;
import org.rocio.spring.bancors.repositories.MovimientoRepository;
import org.rocio.spring.bancors.services.exception.SaldosExepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService{

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> listarMovimientos() {
        return (List<Movimiento>)movimientoRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Movimiento> guardarMovimiento(Long idCuenta, Movimiento movimiento) {
        Optional<Cuenta>cu = cuentaRepository.findById(idCuenta);
        if(cu.isPresent()){
            Cuenta cuentaBD= cu.get();
           if(movimiento.getTipoMovimiento().equals("EGRESO") && cuentaBD.getSaldo() < movimiento.getValor()){
               throw new SaldosExepcion("Saldo insuficiente para realizar el movimiento.");
            }
            cuentaBD.setSaldo(cuentaBD.getSaldo()+movimiento.getValor());
            cuentaRepository.save(cuentaBD);

            movimiento.setFecha(LocalDateTime.now());
            movimiento.setSaldo(cuentaBD.getSaldo());
            movimiento.setCuenta(cuentaBD);
            movimientoRepository.save(movimiento);

            return Optional.of(movimientoRepository.save(movimiento));

        }

        return  Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarMovimiento(Long id) {
         movimientoRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> buscarPorCliente(Long idCliente) {
        return List.of();
    }
}
