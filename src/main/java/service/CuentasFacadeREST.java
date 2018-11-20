/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import entities.Cuentas;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author bryan
 */
@Stateless
@Path("cuentas")
public class CuentasFacadeREST extends AbstractFacade<Cuentas> {

    @PersistenceContext(unitName = "com.ues.fmocc.sv.contables.2018_SIC2018_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public CuentasFacadeREST() {
        super(Cuentas.class);
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void create(String json) {
        System.out.println("POST");
        Gson g = new Gson();
        Cuentas c = g.fromJson(json, Cuentas.class);
        System.out.println(json);
        //super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Cuentas entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String find(@PathParam("id") String id) {
        System.out.println("GETTO");
        return new Gson().toJson(super.find(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String cuentas() {
        return new Gson().toJson(super.getCuentas());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
