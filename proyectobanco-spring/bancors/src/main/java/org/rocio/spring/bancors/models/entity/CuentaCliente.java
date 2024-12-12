package org.rocio.spring.bancors.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cuentas-clientes")
public class CuentaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cliente_id", unique = true)
    private Long clienteId;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(!(obj instanceof  CuentaCliente)){
            return  false;
        }
        CuentaCliente o= (CuentaCliente) obj;
        return this.clienteId!=null && this.clienteId.equals((o.clienteId));
    }
}
