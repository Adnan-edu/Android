/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restmemory.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import restmemory.Memoir;

/**
 *
 * @author adnan
 */
@Stateless
@Path("restmemory.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "MovieMemoryPU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
//        em.persist(entity.getCinemaid());
//        em.persist(entity.getPersonid());
//        em.persist(entity);
        
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
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
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByMoviename/{moviename}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByMoviename(@PathParam("moviename") String moviename) {
        Query query = em.createNamedQuery("Memoir.findByMoviename");
        query.setParameter("moviename", moviename);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }
    @GET
    @Path("findByMoviereldate/{moviereldate}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByMoviereldate(@PathParam("moviereldate") String moviereldate) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(moviereldate);
        } catch (ParseException ex) {
            Logger.getLogger(MemoirFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        Query query = em.createNamedQuery("Memoir.findByMoviereldate");
        query.setParameter("moviereldate", date1);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }
    @GET
    @Path("findByWatchingdatetime/{watchingdatetime}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByWatchingdatetime(@PathParam("watchingdatetime") String watchingdatetime) throws ParseException {
        Timestamp datetime = Timestamp.valueOf(watchingdatetime);
        Query query = em.createNamedQuery("Memoir.findByWatchingdatetime");
        query.setParameter("watchingdatetime", datetime);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }    
    @GET
    @Path("findByComment/{comment}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByComment(@PathParam("comment") String comment) {
        Query query = em.createNamedQuery("Memoir.findByComment");
        query.setParameter("comment", comment);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }
    @GET
    @Path("findByPresonid/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByPresonid(@PathParam("personid") int personid) {
        Query query = em.createNamedQuery("Memoir.findByPresonid");
        query.setParameter("personid", personid);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }
    @GET
    @Path("findByCinemaid/{cinemaid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByCinemaid(@PathParam("cinemaid") int cinemaid) {
        Query query = em.createNamedQuery("Memoir.findByCinemaid");
        query.setParameter("cinemaid", cinemaid);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }

    @GET
    @Path("findByRatingscore/{ratingscore}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByRatingscore(@PathParam("ratingscore") double ratingscore) {
        Query query = em.createNamedQuery("Memoir.findByRatingscore");
        query.setParameter("ratingscore", ratingscore);
        List<Memoir> memoirs = query.getResultList();
        return memoirs;
    }
    // 3 - C
    @GET
    @Path("findByMemoCinemaDynamic/{cinemaname}/{moviename}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findByMemoCinemaDynamic(@PathParam("cinemaname") String cinemaname, @PathParam("moviename") String moviename) {
        TypedQuery<Memoir[]> q = em.createQuery("SELECT m.moviename, m.moviereldate, c.cinemaname FROM Memoir m, Cinema c WHERE"
                + " m.moviename = :moviename AND c.cinemaname = :cinemaname AND m.cinemaid.cinemaname = :cinemaname", Memoir[].class);
        q.setParameter("cinemaname",cinemaname);
        q.setParameter("moviename",moviename);
        
        List<Memoir[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) 
        {
            
            JsonObject personObject = Json.createObjectBuilder().
                add("moviename", (String)row[0])
                .add("moviereldate",row[1].toString())
                .add("cinemaname", (String)row[2]).build();
                arrayBuilder.add(personObject);                
          
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }
    // 3 - D
    @GET
    @Path("findByMemoCinemaSt/{cinemaname}/{moviename}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findByMemoCinemaSt(@PathParam("cinemaname") String cinemaname, @PathParam("moviename") String moviename) {
        Query query = em.createNamedQuery("Memoir.findByMemoCinSt");
        query.setParameter("cinemaname", cinemaname);
        query.setParameter("moviename",moviename);
        List<Memoir[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) 
        {
            
            JsonObject personObject = Json.createObjectBuilder().
                add("moviename", (String)row[0])
                .add("moviereldate",row[1].toString())
                .add("cinemaname", (String)row[2]).build();
                arrayBuilder.add(personObject);                
          
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }
    //Question 4 (a)
    @GET
    @Path("findCinemaByPerson/{personid}/{startingdate}/{endingdate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findCinemaByPerson(@PathParam("personid") int personid, @PathParam("startingdate") String startingdate,
            @PathParam("endingdate") String endingdate) { 
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startingdate);
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endingdate);
        } catch (ParseException ex) {Logger.getLogger(MemoirFacadeREST.class.getName()).log(Level.SEVERE, null, ex);}
        TypedQuery<Memoir[]> q = em.createQuery("SELECT m.personid.personid ,m.cinemaid.suburb, COUNT(m.memoirid) FROM Memoir m"
                + " WHERE m.personid.personid = :personid AND m.watchingdatetime > :startingdate AND m.watchingdatetime < :endingdate "
                + "GROUP BY m.personid.personid, m.cinemaid.suburb", Memoir[].class);
        q.setParameter("personid",personid);
        q.setParameter("startingdate",startDate);
        q.setParameter("endingdate",endDate);
        List<Memoir[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder().
             add("personid", (Integer)row[0])         
            .add("suburb", (String)row[1])
            .add("count", (Long)row[2]).build();
            arrayBuilder.add(personObject);
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }
    
    @GET
    @Path("findByMemCinPerYr/{personid}/{year}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findByMemCinPerYr (@PathParam("personid") int personid, @PathParam("year") String year) {
        TypedQuery<Memoir[]> q = em.createQuery("SELECT EXTRACT(MONTH FROM m.watchingdatetime), COUNT(m.memoirid) FROM Memoir m"
                + " WHERE m.personid.personid = :personid AND EXTRACT(YEAR FROM m.watchingdatetime) = :year"
                + " GROUP BY EXTRACT(MONTH FROM m.watchingdatetime)", Memoir[].class);
        q.setParameter("personid",personid);
        q.setParameter("year",year);
        List<Memoir[]> queryList = q.getResultList();
        ArrayList<String> listOfMonths = new ArrayList<String>();
	listOfMonths.add("January");listOfMonths.add("February");
	listOfMonths.add("March");listOfMonths.add("April");
	listOfMonths.add("May");listOfMonths.add("June");
	listOfMonths.add("July");listOfMonths.add("August");
	listOfMonths.add("September");listOfMonths.add("October");
	listOfMonths.add("November"); listOfMonths.add("December");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder().
            add("month", (String)listOfMonths.get((int) row[0] - 1))
           .add("count", (Long)row[1]).build();
            arrayBuilder.add(personObject);
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }

    @GET
    @Path("findMaxMovRatByPrsn/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findMaxMovRatByPrsn (@PathParam("personid") int personid) {
        TypedQuery<Memoir[]> q = em.createQuery("SELECT m.moviename, m.ratingscore, m.moviereldate FROM Memoir m"
                + " WHERE m.personid.personid = :personid", Memoir[].class);
        q.setParameter("personid",personid);
        List<Memoir[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String pattern = "yyyy-MM-dd";
        float maxRat = 0;
        DateFormat df = new SimpleDateFormat(pattern);
        for (Object[] row : queryList) {
            if((Float)row[1] > maxRat){
                maxRat = (Float)row[1];
            } 
        }
        for (Object[] row : queryList) {
            if((Float)row[1] == maxRat){
                String dateAsString = df.format(row[2]);
                JsonObject personObject = Json.createObjectBuilder().
                add("moviename", (String)row[0])
               .add("ratingscore", (Float)row[1])
               .add("moviereldate", dateAsString).build();
                arrayBuilder.add(personObject);                
            }
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }

    @GET
    @Path("findMovSameRealDateWatch/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findMovSameRealDateWatch (@PathParam("personid") int personid) {
        TypedQuery<Memoir[]> q = em.createQuery("SELECT m.moviename, EXTRACT(YEAR FROM m.moviereldate) FROM Memoir m"
                + " WHERE m.personid.personid = :personid AND EXTRACT(YEAR FROM m.moviereldate) = EXTRACT(YEAR FROM m.watchingdatetime)", Memoir[].class);
        q.setParameter("personid",personid);
        List<Memoir[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) 
        {
            
            JsonObject personObject = Json.createObjectBuilder().
                add("moviename", (String)row[0])
               .add("movierelyear", (Integer)row[1]).build();
                arrayBuilder.add(personObject);                
          
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }

    @GET
    @Path("findRecntYrssMovByRating/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findRecntYrssMovByRating (@PathParam("personid") int personid){
        LocalDate localdate = LocalDate.now();
        String year = Integer.toString(localdate.getYear());
        TypedQuery<Memoir[]> q = em.createQuery("SELECT m.moviename, m.moviereldate, m.ratingscore FROM Memoir m"
                + " WHERE m.personid.personid = :personid AND EXTRACT(YEAR FROM m.moviereldate) = :year ORDER BY m.ratingscore DESC", Memoir[].class);
        q.setParameter("personid",personid);
        q.setParameter("year",year);
        int count = 0;
        List<Memoir[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) 
        {
            if(count == 5){
                break;
            }
            JsonObject personObject = Json.createObjectBuilder().
                add("moviename", (String)row[0])
               .add("moviereldate", row[1].toString())
               .add("ratingscore", (Float)row[2]).build();
                arrayBuilder.add(personObject);   
            count++;
          
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    }

    @GET
    @Path("findRemakeMovieWatch/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findRemakeMovieWatch (@PathParam("personid") int personid) {
        TypedQuery<Memoir[]> q = em.createQuery("SELECT m.moviename, m.moviereldate FROM Memoir m INNER JOIN Memoir m2"
                + " WHERE m.personid.personid = :personid AND m.moviename = m2.moviename AND m.moviereldate <> m2.moviereldate", Memoir[].class);
        q.setParameter("personid",personid);
        List<Memoir[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) 
        {
            
            JsonObject personObject = Json.createObjectBuilder().
                add("moviename", (String)row[0])
               .add("moviereldate", (String)row[1].toString()).build();
                arrayBuilder.add(personObject);                
          
         }
         JsonArray jArray = arrayBuilder.build();
         return jArray;
    } 
    
}
