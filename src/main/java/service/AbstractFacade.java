/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author bryan
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {  
        try {
            getEntityManager().persist(entity);
        } catch (Exception e) {
            System.out.println("create: "+e);
        }
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    List<Object[]> executeObjectArray(String query){
        List<Object[]> lis = new ArrayList<>();

        try {
            Query q = getEntityManager().createQuery(query);
            lis = q.getResultList();
        } catch (Exception e) {
            System.out.println("ex" + e);
        }
        return lis;
    }
    
     Object executeObject(String query){
        Object lis = "";

        try {
            Query q = getEntityManager().createQuery(query);
            if(q.getResultList().isEmpty()){
                return "0.0";
            }
            lis = q.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("ex" + e);
        }
        return lis;
    }
    
   

    public List<Object[]> getCuentas() {
        return executeObjectArray("SELECT m.idCuenta, m.cuenta FROM Cuentas m ORDER BY m.idCuenta");
    }
    
    public List<Object[]> getTransacciones() {
        return executeObjectArray("SELECT t.idAbono.cuenta, t.idCargo.cuenta, t.monto, CONCAT(t.fecha,\"\"), t.comentario FROM Transaccion t");
    }
    
    public String getAbonos(String id) {
        return executeObject("SELECT SUM(m.monto) FROM Transaccion m WHERE m.idAbono.idCuenta =\""+id+"\" GROUP BY m.idAbono.cuenta").toString();
    }
    
    public String getCargos(String id) {
        return executeObject("SELECT SUM(m.monto) FROM Transaccion m WHERE m.idCargo.idCuenta =\""+id+"\" GROUP BY m.idCargo.cuenta").toString();
    }
    
    public List<Object[]> getCuentasMayor() {
        return executeObjectArray("SELECT m.idCuenta, m.cuenta FROM Cuentas m WHERE m.idCuenta IN (SELECT DISTINCT t.idCargo.idCuenta FROM Transaccion t) OR m.idCuenta IN (SELECT DISTINCT p.idAbono.idCuenta FROM Transaccion p) ORDER BY m.idCuenta");
    }
    
    public String getTotalCuentas() {
        return executeObject("SELECT SUM(f.monto) FROM Transaccion f").toString();
    }

}
