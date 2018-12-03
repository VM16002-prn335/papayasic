/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Cuentas;
import entities.Transaccion;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
            str+="{\"cargo\":\""+li[0]+"\",\"abono\":\""+li[1]+"\",\"monto\":\""+li[2]+"\",\"fecha\":\""+li[3]+"\",\"comentario\":\""+li[4]+"\"},";
        }
        str = str.replaceAll("\\,$", "");
        str+="]}";
        
        return str;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addTransacion(String json){
        System.out.println("TRANSACCION\n"+json);
        JsonObject jo = (JsonObject) new JsonParser().parse(json);
        Transaccion t = new Transaccion();
        
        t.setMonto(Double.parseDouble(jo.get("monto").toString().replaceAll("\"", "")));
        t.setFecha(new Date(Integer.parseInt(jo.get("fecha").toString().replaceAll("\"", "").split("-")[0])-1900, Integer.parseInt(jo.get("fecha").toString().replaceAll("\"", "").split("-")[1]), Integer.parseInt(jo.get("fecha").toString().replaceAll("\"", "").split("-")[2])));
        t.setComentario(jo.get("comentario").toString().replaceAll("\"", ""));
        t.setIdAbono(new Cuentas(jo.get("idAbono").toString().replaceAll("\"", "")));
        t.setIdCargo(new Cuentas(jo.get("idCargo").toString().replaceAll("\"", "")));
        System.out.println(t.getMonto() + "\t" + t.getComentario() + "\t" + t.getFecha() + "\t" + t.getIdAbono() + "\t" + t.getIdCargo());
       
        super.create(t);
    }
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
