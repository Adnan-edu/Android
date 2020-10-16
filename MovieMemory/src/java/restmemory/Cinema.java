/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restmemory;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author adnan
 */
@Entity
@Table(name = "CINEMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinema.findAll", query = "SELECT c FROM Cinema c")
    , @NamedQuery(name = "Cinema.findByCinemaid", query = "SELECT c FROM Cinema c WHERE c.cinemaid = :cinemaid")
    , @NamedQuery(name = "Cinema.findByCinemaname", query = "SELECT c FROM Cinema c WHERE c.cinemaname = :cinemaname")
    , @NamedQuery(name = "Cinema.findBySuburb", query = "SELECT c FROM Cinema c WHERE c.suburb = :suburb")
    , @NamedQuery(name = "Cinema.findByPostcode", query = "SELECT c FROM Cinema c WHERE c.postcode = :postcode")})
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CINEMAID")
    private Integer cinemaid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CINEMANAME")
    private String cinemaname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SUBURB")
    private String suburb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "POSTCODE")
    private String postcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinemaid")
    private Collection<Memoir> memoirCollection;

    public Cinema() {
    }

    public Cinema(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Cinema(Integer cinemaid, String cinemaname, String suburb, String postcode) {
        this.cinemaid = cinemaid;
        this.cinemaname = cinemaname;
        this.suburb = suburb;
        this.postcode = postcode;
    }

    public Integer getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @XmlTransient
    public Collection<Memoir> getMemoirCollection() {
        return memoirCollection;
    }

    public void setMemoirCollection(Collection<Memoir> memoirCollection) {
        this.memoirCollection = memoirCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cinemaid != null ? cinemaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinema)) {
            return false;
        }
        Cinema other = (Cinema) object;
        if ((this.cinemaid == null && other.cinemaid != null) || (this.cinemaid != null && !this.cinemaid.equals(other.cinemaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restmemory.Cinema[ cinemaid=" + cinemaid + " ]";
    }
    
}
