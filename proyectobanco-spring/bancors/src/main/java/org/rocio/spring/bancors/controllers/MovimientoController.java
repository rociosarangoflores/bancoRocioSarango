package org.rocio.spring.bancors.controllers;

import jakarta.validation.Valid;
import org.rocio.spring.bancors.models.entity.Movimiento;
import org.rocio.spring.bancors.services.MovimientoService;
import org.rocio.spring.bancors.services.exception.SaldosExepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("movimientos")
public class MovimientoController {

    @Autowired
    MovimientoService movimientoService;
    // public ResponseEntity<?> editar(@Valid @RequestBody  Usuario usuario, BindingResult result, @PathVariable Long id){

    @PostMapping("/{cuentaId}")
    public ResponseEntity<?> registrarMovimiento(@Valid @RequestBody Movimiento movimiento, BindingResult result, @PathVariable Long cuentaId){
         if(result.hasErrors()){
            return validar(result);
        }
        try{
            Optional<Movimiento> movimientoOpt= movimientoService.guardarMovimiento(cuentaId, movimiento);
            if(movimientoOpt.isPresent()){
                return ResponseEntity.ok(movimientoOpt.get());
            }
            return ResponseEntity.notFound().build();
        }catch (SaldosExepcion se){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", se.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "Saldo no disponible "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
