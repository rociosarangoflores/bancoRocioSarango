package org.rocio.spring.bancors.controllers;

import jakarta.validation.Valid;
import org.rocio.spring.bancors.models.entity.Cliente;
import org.rocio.spring.bancors.services.ClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    public ClienteServices clienteServices;

    @GetMapping
    private ResponseEntity<List<Cliente>> listarClientes(){
        return ResponseEntity.ok(clienteServices.listarClientes());
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Cliente> clienteBD= clienteServices.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteBD);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> editar(@Valid @RequestBody Cliente cliente , BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Cliente>o = clienteServices.buscarClientePorId(id);
        if(o.isPresent()){
            Cliente clienteBd = o.get();
            clienteBd.setNombre(cliente.getNombre());
            clienteBd.setGenero(cliente.getGenero());
            clienteBd.setEdad(cliente.getEdad());
            clienteBd.setDireccion(cliente.getDireccion());
            clienteBd.setTelefono(cliente.getTelefono());
            clienteBd.setEstado(cliente.getEstado());
            clienteBd.setPassword(cliente.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteServices.crearCliente(clienteBd));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Cliente> o = clienteServices.buscarClientePorId(id);
        if(o.isPresent()){
            clienteServices.eliminarClientePorId(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "El campo"+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
