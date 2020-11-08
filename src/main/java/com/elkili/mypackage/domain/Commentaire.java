package com.elkili.mypackage.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Commentaire.
 */
@Entity
@Table(name = "commentaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("commentaires")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("commentaires")
    private Poste poste;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Commentaire commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public User getUser() {
        return user;
    }

    public Commentaire user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Poste getPoste() {
        return poste;
    }

    public Commentaire poste(Poste poste) {
        this.poste = poste;
        return this;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commentaire)) {
            return false;
        }
        return id != null && id.equals(((Commentaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
            "id=" + getId() +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
