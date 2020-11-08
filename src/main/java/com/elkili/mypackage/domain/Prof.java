package com.elkili.mypackage.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Prof.
 */
@Entity
@Table(name = "prof")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prof implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_prof")
    private String nomProf;

    @Column(name = "prenom_prof")
    private String prenomProf;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProf() {
        return nomProf;
    }

    public Prof nomProf(String nomProf) {
        this.nomProf = nomProf;
        return this;
    }

    public void setNomProf(String nomProf) {
        this.nomProf = nomProf;
    }

    public String getPrenomProf() {
        return prenomProf;
    }

    public Prof prenomProf(String prenomProf) {
        this.prenomProf = prenomProf;
        return this;
    }

    public void setPrenomProf(String prenomProf) {
        this.prenomProf = prenomProf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prof)) {
            return false;
        }
        return id != null && id.equals(((Prof) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prof{" +
            "id=" + getId() +
            ", nomProf='" + getNomProf() + "'" +
            ", prenomProf='" + getPrenomProf() + "'" +
            "}";
    }
}
