package org.rocio.spring.bancors.repositories;

import org.rocio.spring.bancors.models.entity.Cliente;
import org.rocio.spring.bancors.models.entity.Cuenta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CuentaRepository extends CrudRepository<Cuenta, Long> {

    @Query("delete from CuentaCliente cu where cu.clienteId=?1")
    @Modifying
    void eliminarCuentaClientePorId(Long id);

   // List<Cuenta> findByClienteIdAndFechas(Long clientId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
