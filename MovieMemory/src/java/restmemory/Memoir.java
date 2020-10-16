/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restmemory;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adnan
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findByMemoirid", query = "SELECT m FROM Memoir m WHERE m.memoirid = :memoirid")
    , @NamedQuery(name = "Memoir.findByMoviename", query = "SELECT m FROM Memoir m WHERE m.moviename = :moviename")
    , @NamedQuery(name = "Memoir.findByMoviereldate", query = "SELECT m FROM Memoir m WHERE m.moviereldate = :moviereldate")
    , @NamedQuery(name = "Memoir.findByWatchingdatetime", query = "SELECT m FROM Memoir m WHERE m.watchingdatetime = :watchingdatetime")
    , @NamedQuery(name = "Memoir.findByComment", query = "SELECT m FROM Memoir m WHERE m.comment = :comment")
    , @NamedQuery(name = "Memoir.findByPresonid", query = "SELECT m FROM Memoir m WHERE m.personid.personid = :personid")
    , @NamedQuery(name = "Memoir.findByCinemaid", query = "SELECT m FROM Memoir m WHERE m.cinemaid.cinemaid = :cinemaid")
    , @NamedQuery(name = "Memoir.findByMemoCinSt", query = "SELECT m.moviename, m.moviereldate, c.cinemaname FROM Memoir m, Cinema c"
            + " WHERE m.moviename = :moviename AND c.cinemaname = :cinemaname AND m.cinemaid.cinemaname = :cinemaname")
    , @NamedQuery(name = "Memoir.findByRatingscore", query = "SELECT m FROM Memoir m WHERE m.ratingscore = :ratingscore")})
public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MEMOIRID")
    private Integer memoirid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MOVIENAME")
    private String moviename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOVIERELDATE")
    @Temporal(TemporalType.DATE)
    private Date moviereldate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WATCHINGDATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date watchingdatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "COMMENT")
    private String comment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATINGSCORE")
    private float ratingscore;
    @JoinColumn(name = "CINEMAID", referencedColumnName = "CINEMAID")
    @ManyToOne(optional = false)
    private Cinema cinemaid;
    @JoinColumn(name = "PERSONID", referencedColumnName = "PERSONID")
    @ManyToOne(optional = false)
    private Person personid;

    public Memoir() {
    }

    public Memoir(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public Memoir(Integer memoirid, String moviename, Date moviereldate, Date watchingdatetime, String comment, float ratingscore) {
        this.memoirid = memoirid;
        this.moviename = moviename;
        this.moviereldate = moviereldate;
        this.watchingdatetime = watchingdatetime;
        this.comment = comment;
        this.ratingscore = ratingscore;
    }

    public Integer getMemoirid() {
        return memoirid;
    }

    public void setMemoirid(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getMoviereldate() {
        return moviereldate;
    }

    public void setMoviereldate(Date moviereldate) {
        this.moviereldate = moviereldate;
    }

    public Date getWatchingdatetime() {
        return watchingdatetime;
    }

    public void setWatchingdatetime(Date watchingdatetime) {
        this.watchingdatetime = watchingdatetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(float ratingscore) {
        this.ratingscore = ratingscore;
    }

    public Cinema getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinema cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memoirid != null ? memoirid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.memoirid == null && other.memoirid != null) || (this.memoirid != null && !this.memoirid.equals(other.memoirid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restmemory.Memoir[ memoirid=" + memoirid + " ]";
    }
    
}
