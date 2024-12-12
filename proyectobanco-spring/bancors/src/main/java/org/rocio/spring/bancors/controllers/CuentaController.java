package org.rocio.spring.bancors.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.rocio.spring.bancors.models.entity.Cliente;
import org.rocio.spring.bancors.models.entity.Cuenta;
import org.rocio.spring.bancors.services.CuentaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("cuentas")
public class CuentaController {

    @Autowired
    CuentaServices cuentaServices;


    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas(){
        return ResponseEntity.ok(cuentaServices.listarCuentas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>detalleCuenta(@PathVariable Long id){
        Optional<Cuenta> o= cuentaServices.buscarCuentaByIdCliente(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return  ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?>crearCuenta (@Valid @RequestBody Cuenta cuenta, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        Cuenta cuentaBd= cuentaServices.guardarCuenta(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaBd);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> editarCuenta(@Valid @RequestBody Cuenta cuenta , BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Cuenta>o = cuentaServices.buscarCuentaById(id);
        if(o.isPresent()){
            Cuenta cuentaBd = o.get();
            cuentaBd.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaBd.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaBd.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaBd.setEstado(cuenta.getEstado());
            cuentaBd.setSaldo(cuenta.getSaldo());

            return ResponseEntity.status(HttpStatus.CREATED).body(cuentaServices.guardarCuenta(cuentaBd));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Long id){
        Optional<Cuenta> o = cuentaServices.buscarCuentaById(id);
        if(o.isPresent()){
            cuentaServices.eliminarCuenta(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/asignar-cliente/{clienteId}")
    public ResponseEntity<?> asignarClienteCuenta (@RequestBody Cliente cliente, @PathVariable Long clienteId){

        Optional<Cliente>o;
        try {
            o= cuentaServices.asignarCuentaCliente(cliente, clienteId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje ", "No existe el cliente por el id o error " +
                            "en comunicacion: "+e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping ("/crear-cliente/{cuentaId}")
    public ResponseEntity<?> crearCliente (@RequestBody Cliente cliente, @PathVariable Long cuentaId){

        Optional<Cliente>o;
        try {
            o= cuentaServices.crearClienteCuenta(cliente, cuentaId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje ", "No pudo crear el cliente o error " +
                            "en comunicacion: "+e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/eliminar-cliente/{clienteId}")
    public ResponseEntity<?> eliminarCliente (@RequestBody Cliente cliente, @PathVariable Long clienteId){

        Optional<Cliente>o;
        try {
            o= cuentaServices.eliminarClienteCuenta(cliente, clienteId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje ", "No existe el cliente por el id o error " +
                            "en comunicacion: "+e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-cuenta-cliente/{id}")
    public ResponseEntity<?> eliminarCuentaClientePorId(@PathVariable Long id){
        cuentaServices.eliminarCuentaByIdCliente(id);
        return ResponseEntity.noContent().build();
    }



    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "El campo"+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
