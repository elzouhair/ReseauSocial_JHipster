package com.elkili.mypackage.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.elkili.mypackage.domain.enumeration.Type;

/**
 * A Poste.
 */
@Entity
@Table(name = "poste")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poste implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Lob
    @Column(name = "fichier")
    private byte[] fichier;

    @Column(name = "fichier_content_type")
    private String fichierContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("postes")
    private User user;

    @OneToMany(mappedBy = "poste")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commentaire> commentaires = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Poste description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public Poste type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public Poste fichier(byte[] fichier) {
        this.fichier = fichier;
        return this;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public String getFichierContentType() {
        return fichierContentType;
    }

    public Poste fichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
        return this;
    }

    public void setFichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
    }

    public User getUser() {
        return user;
    }

    public Poste user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public Poste commentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public Poste addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setPoste(this);
        return this;
    }

    public Poste removeCommentaire(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setPoste(null);
        return this;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poste)) {
            return false;
        }
        return id != null && id.equals(((Poste) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Poste{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", fichierContentType='" + getFichierContentType() + "'" +
            "}";
    }
}
