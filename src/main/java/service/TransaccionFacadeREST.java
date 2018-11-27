/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Transaccion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author bryan
 */
@Stateless
@Path("transaccion")
public class TransaccionFacadeREST extends AbstractFacade<Transaccion> {

    @PersistenceContext(unitName = "com.ues.fmocc.sv.contables.2018_SIC2018_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public TransaccionFacadeREST() {
        super(Transaccion.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String transacciones(){
        System.out.println("GET TRANSACCIONES");
        String str = "{\"res\":[";
        for (Object[] li : super.getTransacciones()) {
            str+="{\"cargo\":\""+li[0]+"\",\"abono\":\""+li[1]+"\",\"monto\":\""+li[2]+"\",\"fecha\":\""+li[3]+"\"},";
        }
        str = str.replaceAll("\\,$", "");
        str+="]}";
        
        return str;
    }
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
