/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restmemory.service;

import java.util.List;
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
import restmemory.Cinema;

/**
 *
 * @author adnan
 */
@Stateless
@Path("restmemory.cinema")
public class CinemaFacadeREST extends AbstractFacade<Cinema> {

    @PersistenceContext(unitName = "MovieMemoryPU")
    private EntityManager em;

    public CinemaFacadeREST() {
        super(Cinema.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Cinema entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cinema entity) {
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
    public Cinema find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByCinemaname/{cinemaname}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findByCinemaname(@PathParam("cinemaname") String cinemaname) {
        Query query = em.createNamedQuery("Cinema.findByCinemaname");
        query.setParameter("cinemaname", cinemaname);
        List<Cinema> cinemas = query.getResultList();
        return cinemas;
    }
    @GET
    @Path("findBySuburb/{suburb}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findBySuburb(@PathParam("suburb") String suburb) {
        Query query = em.createNamedQuery("Cinema.findBySuburb");
        query.setParameter("suburb", suburb);
        List<Cinema> cinemas = query.getResultList();
        return cinemas;
    }
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findByPostcode(@PathParam("postcode") String postcode) {
        Query query = em.createNamedQuery("Cinema.findByPostcode");
        query.setParameter("postcode", postcode);
        List<Cinema> cinemas = query.getResultList();
        return cinemas;
    }
    
}
