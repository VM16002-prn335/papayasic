/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bryan
 */
@Entity
@Table(name = "cuentas", catalog = "dccef5jnh016ks", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c")
    , @NamedQuery(name = "Cuentas.findByAjuste", query = "SELECT c FROM Cuentas c WHERE c.ajuste = :ajuste")
    , @NamedQuery(name = "Cuentas.findByCuenta", query = "SELECT c FROM Cuentas c WHERE c.cuenta = :cuenta")
    , @NamedQuery(name = "Cuentas.findByIdCuenta", query = "SELECT c FROM Cuentas c WHERE c.idCuenta = :idCuenta")})
public class Cuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "ajuste")
    private Boolean ajuste;
    @Size(max = 2147483647)
    @Column(name = "cuenta", length = 2147483647)
    private String cuenta;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id_cuenta", nullable = false, length = 2147483647)
    private String idCuenta;
    @OneToMany(mappedBy = "idAbono")
    private List<Transaccion> transaccionList;
    @OneToMany(mappedBy = "idCargo")
    private List<Transaccion> transaccionList1;
    @OneToMany(mappedBy = "sucesor")
    private List<Cuentas> cuentasList;
    @JoinColumn(name = "sucesor", referencedColumnName = "id_cuenta")
    @ManyToOne
    private Cuentas sucesor;

    public Cuentas() {
    }

    public Cuentas(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Boolean getAjuste() {
        return ajuste;
    }

    public void setAjuste(Boolean ajuste) {
        this.ajuste = ajuste;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList1() {
        return transaccionList1;
    }

    public void setTransaccionList1(List<Transaccion> transaccionList1) {
        this.transaccionList1 = transaccionList1;
    }

    @XmlTransient
    public List<Cuentas> getCuentasList() {
        return cuentasList;
    }

    public void setCuentasList(List<Cuentas> cuentasList) {
        this.cuentasList = cuentasList;
    }

    public Cuentas getSucesor() {
        return sucesor;
    }

    public void setSucesor(Cuentas sucesor) {
        this.sucesor = sucesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuentas)) {
            return false;
        }
        Cuentas other = (Cuentas) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cuentas[ idCuenta=" + idCuenta + " ]";
    }
    
}
