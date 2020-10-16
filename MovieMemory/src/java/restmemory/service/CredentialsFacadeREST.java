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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restmemory.Credentials;
import restmemory.Person;

/**
 *
 * @author adnan
 */
@Stateless
@Path("restmemory.credentials")
public class CredentialsFacadeREST extends AbstractFacade<Credentials> {

    @PersistenceContext(unitName = "MovieMemoryPU")
    private EntityManager em;

    public CredentialsFacadeREST() {
        super(Credentials.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Credentials entity) {
        
        super.create(entity);
        em.persist(entity.getPersonid());
        em.persist(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credentials entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Credentials find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @GET
    @Path("findByPassword/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findByPassword(@PathParam("password") String password) {
        Query query = em.createNamedQuery("Credentials.findByPassword");
        query.setParameter("password", password);
        List<Credentials> credentials = query.getResultList();
        return credentials;
    }
    @GET
    @Path("findBySignupdate/{signupdate}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findBySignupdate(@PathParam("signupdate") String signupdate) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(signupdate);
        } catch (ParseException ex) {
            Logger.getLogger(CredentialsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        Query query = em.createNamedQuery("Credentials.findBySignupdate");
        query.setParameter("signupdate", date1);
        List<Credentials> credentials = query.getResultList();
        return credentials;
    }
    @GET
    @Path("findByPersonId/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findByPersonId(@PathParam("personid") int personid) {
        Query query = em.createNamedQuery("Credentials.findByPersonId");
        query.setParameter("personid", personid);
        List<Credentials> credentials = query.getResultList();
        return credentials;
    }
    @GET
    @Path("findByIdPassword/{username}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Person findByPersonId(@PathParam("username") String username, @PathParam("password") String password) {
        Query query = em.createNamedQuery("Credentials.findByUsername");
        query.setParameter("username", username);
        List<Credentials> credentials = query.getResultList();
        if(credentials.size()==1 && credentials.get(0).getPassword().equals(password)){
            Query queryPerson = em.createNamedQuery("Person.findByPersonid");
            queryPerson.setParameter("personid", credentials.get(0).getPersonid().getPersonid());
            List<Person> person = queryPerson.getResultList();
            return person.get(0);
        }
        return null;
    }
    
}
