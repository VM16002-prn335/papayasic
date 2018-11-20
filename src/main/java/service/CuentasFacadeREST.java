/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Cuentas;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String cuentas() {
        System.out.println("GETTO");
        return new Gson().toJson(super.getCuentas());
//        return Response.ok().entity(new Gson().toJson(super.getCuentas()))
//                .header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
//                .allow("OPTIONS").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCuenta(String json) {
        System.out.println("POST");
        System.out.println(json);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(json);
        Cuentas c = new Cuentas();
        
        c.setIdCuenta(jo.get("idCuenta").toString());
        c.setCuenta(jo.get("cuenta").toString());
        if(jo.get("ajuste").toString().equalsIgnoreCase("true")){
            c.setAjuste(Boolean.TRUE);
        }else{
            c.setAjuste(Boolean.FALSE);
        }
        c.setSucesor(new Cuentas(jo.get("sucesor").toString()));
        
        System.out.println(c.getIdCuenta()+"  "+c.getAjuste()+"  "+c.getCuenta()+" "+c.getSucesor().getIdCuenta());
        super.create(c);
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
        return new Gson().toJson(super.find(id));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
