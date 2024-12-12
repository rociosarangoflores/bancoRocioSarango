package org.rocio.spring.bancors.models.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private String estado;
    private double saldo;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="cuenta_id")
    private List<CuentaCliente> cuentaClientes;

    @Transient
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "cuenta")
    private List<Movimiento> movimientos;


    public Cuenta() {
        cuentaClientes = new ArrayList<>();
        clientes= new ArrayList<>();
    }

    public void addCuentaCliente(CuentaCliente cuentaCliente){
        cuentaClientes.add(cuentaCliente);
    }

    public void removeCuentaCliente(CuentaCliente cuentaCliente){
        cuentaClientes.remove(cuentaCliente);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<CuentaCliente> getCuentaClientes() {
        return cuentaClientes;
    }

    public void setCuentaClientes(List<CuentaCliente> cuentaClientes) {
        this.cuentaClientes = cuentaClientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
