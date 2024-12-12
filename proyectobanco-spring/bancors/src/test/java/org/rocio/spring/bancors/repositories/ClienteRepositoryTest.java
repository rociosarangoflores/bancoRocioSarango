package org.rocio.spring.bancors.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rocio.spring.bancors.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void guardarClienteTest(){
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

    }
}
