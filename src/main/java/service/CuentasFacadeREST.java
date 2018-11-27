/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

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
        System.out.println("GET CUENTAS.");
        String str = "{\"res\":[";
        for (Object[] li : super.getCuentas()) {
            str+="{\"idCuenta\":\""+li[0]+"\",\"nombre\":\""+li[1]+"\"},";
        }
        str = str.replaceAll("\\,$", "");
        str+="]}";
        return str;
    }
    
    @GET
    @Path("/mayor")
    @Produces(MediaType.APPLICATION_JSON)
    public String mayor(){
        System.out.println("GET MAYOR.");
        String str = "{\"res\":[";
        for (Object[] li : super.getCuentasMayor()) {
            
            str+="{\"cuenta\":\""+li[1]+"\",\"cargo\":\""+super.getCargos(li[0].toString())+"\",\"abono\":\""+super.getAbonos(li[0].toString())+"\"},";
            
        }
        str = str.replaceAll("\\,$", "");
        str+="]}";
        return str;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCuenta(String json) {
        System.out.println("POST");
        System.out.println(json);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(json);
        Cuentas c = new Cuentas();
        
        c.setIdCuenta(jo.get("idCuenta").toString().replaceAll("\"", ""));
        c.setCuenta(jo.get("cuenta").toString().replaceAll("\"", ""));
        if(jo.get("ajuste").toString().equalsIgnoreCase("true")){
            c.setAjuste(Boolean.TRUE);
        }else{
            c.setAjuste(Boolean.FALSE);
        }
        c.setSucesor(new Cuentas(jo.get("sucesor").toString().replaceAll("\"", "")));
        
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

    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
