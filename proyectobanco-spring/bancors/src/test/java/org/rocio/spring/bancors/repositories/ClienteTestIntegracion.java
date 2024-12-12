package org.rocio.spring.bancors.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rocio.spring.bancors.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClienteTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testGuardarYBuscarCliente(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Rocio");
        cliente.setDireccion("Direccion test");
        cliente.setTelefono("1234567890");
        cliente.setEdad(20);
        cliente.setGenero("F");
        cliente.setEstado("True");
        cliente.setPassword("12345678");

        Cliente clienteBD= clienteRepository.save(cliente);

        assertThat(clienteBD).isNotNull();
        assertThat(clienteBD.getId()).isNotNull();
        assertThat(clienteBD.getNombre()).isEqualTo("Rocio");
        assertThat(clienteBD.getDireccion()).isEqualTo("Direccion test");
        assertThat(clienteBD.getTelefono()).isEqualTo("1234567890");
        assertThat(clienteBD.getEdad()).isEqualTo(20);
        assertThat(clienteBD.getGenero()).isEqualTo("F");
        assertThat(clienteBD.getPassword()).isEqualTo("12345678");

        Optional<Cliente> clienteBusq= clienteRepository.findById(clienteBD.getId());
        assertThat(clienteBusq).isPresent();
        assertThat(clienteBusq.get().getId()).isEqualTo(clienteBD.getId());
        assertThat(clienteBusq.get().getNombre()).isEqualTo(clienteBD.getNombre());
        assertThat(clienteBusq.get().getDireccion()).isEqualTo(clienteBD.getDireccion());
        assertThat(clienteBusq.get().getTelefono()).isEqualTo(clienteBD.getTelefono());
        assertThat(clienteBusq.get().getEdad()).isEqualTo(clienteBD.getEdad());
        assertThat(clienteBusq.get().getGenero()).isEqualTo(clienteBD.getGenero());
        assertThat(clienteBusq.get().getPassword()).isEqualTo(clienteBD.getPassword());
    }
}
