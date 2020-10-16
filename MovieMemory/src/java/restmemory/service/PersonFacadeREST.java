/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restmemory.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restmemory.Person;

/**
 *
 * @author adnan
 */
@Stateless
@Path("restmemory.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "MovieMemoryPU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
        //em.persist(entity);
    }
//    @POST
//    @Path("data")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Object createData(Person entity) {
//        em.persist(entity);
//        em.flush();
//        Integer id = entity.getPersonid();;
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        
//        JsonObject memoirObject = Json.createObjectBuilder()
//		    .add("ID", id)
//		    .build();
//        arrayBuilder.add(memoirObject);
//        JsonArray jArray = arrayBuilder.build();
//        return jArray;
//        
//    }
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    
    @GET
    @Path("findByFirstName/{firstname}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByFirstName(@PathParam("firstname") String firstname) {
        Query query = em.createNamedQuery("Person.findByFirstname");
        query.setParameter("firstname", firstname);
        List<Person> persons = query.getResultList();
        return persons;
    }
    
    @GET
    @Path("findBySurname/{surname}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findBySurname(@PathParam("surname") String surname) {
        Query query = em.createNamedQuery("Person.findBySurname");
        query.setParameter("surname", surname);
        List<Person> persons = query.getResultList();
        return persons;
    }

    @GET
    @Path("findByGender/{gender}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByGender(@PathParam("gender") String gender) {
        Query query = em.createNamedQuery("Person.findByGender");
        query.setParameter("gender", gender);
        List<Person> persons = query.getResultList();
        return persons;
    }

    @GET
    @Path("findByDob/{dob}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByDob(@PathParam("dob") String dob) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException ex) {
            Logger.getLogger(PersonFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        Query query = em.createNamedQuery("Person.findByDob");
        query.setParameter("dob", date1);
        List<Person> persons = query.getResultList();
        return persons;
    }
    
    @GET
    @Path("findByStreetnumber/{streetnumber}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByStreetnumber(@PathParam("streetnumber") String streetnumber) {
        Query query = em.createNamedQuery("Person.findByStreetnumber");
        query.setParameter("streetnumber", streetnumber);
        List<Person> persons = query.getResultList();
        return persons;
    }

    @GET
    @Path("findByStreetname/{streetname}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByStreetname(@PathParam("streetname") String streetname) {
        Query query = em.createNamedQuery("Person.findByStreetname");
        query.setParameter("streetname", streetname);
        List<Person> persons = query.getResultList();
        return persons;
    }


    @GET
    @Path("findByState/{state}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByState(@PathParam("state") String state) {
        Query query = em.createNamedQuery("Person.findByState");
        query.setParameter("state", state);
        List<Person> persons = query.getResultList();
        return persons;
    }    
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findByPostcode(@PathParam("postcode") String postcode) {
        Query query = em.createNamedQuery("Person.findByPostcode");
        query.setParameter("postcode", postcode);
        List<Person> persons = query.getResultList();
        return persons;
    }
    
    @GET
    @Path("searchPersons/{streetname}/{state}/{postcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> searchPersons(@PathParam("streetname") String streetname, @PathParam("state") String state, @PathParam("postcode") String postcode) {
        TypedQuery<Person> query = em.createQuery ("SELECT p FROM Person p WHERE p.streetname = :streetname"
                + " AND p.state = :state AND p.postcode = :postcode", Person.class);
        query.setParameter("streetname", streetname); 
        query.setParameter("state", state);
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
