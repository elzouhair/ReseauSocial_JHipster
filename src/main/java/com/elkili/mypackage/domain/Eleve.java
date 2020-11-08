package com.elkili.mypackage.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_eleve")
    private String nomEleve;

    @Column(name = "prenom_eleve")
    private String prenomEleve;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public Eleve nomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
        return this;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    public String getPrenomEleve() {
        return prenomEleve;
    }

    public Eleve prenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
        return this;
    }

    public void setPrenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eleve)) {
            return false;
        }
        return id != null && id.equals(((Eleve) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Eleve{" +
            "id=" + getId() +
            ", nomEleve='" + getNomEleve() + "'" +
            ", prenomEleve='" + getPrenomEleve() + "'" +
            "}";
    }
}
