package com.elkili.mypackage.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.elkili.mypackage.domain.Eleve} entity.
 */
public class EleveDTO implements Serializable {

    private Long id;

    private String nomEleve;

    private String prenomEleve;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    public String getPrenomEleve() {
        return prenomEleve;
    }

    public void setPrenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EleveDTO eleveDTO = (EleveDTO) o;
        if (eleveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eleveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EleveDTO{" +
            "id=" + getId() +
            ", nomEleve='" + getNomEleve() + "'" +
            ", prenomEleve='" + getPrenomEleve() + "'" +
            "}";
    }
}
