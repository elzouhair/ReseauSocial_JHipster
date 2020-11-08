package com.elkili.mypackage.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.elkili.mypackage.domain.Prof} entity.
 */
public class ProfDTO implements Serializable {

    private Long id;

    private String nomProf;

    private String prenomProf;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProf() {
        return nomProf;
    }

    public void setNomProf(String nomProf) {
        this.nomProf = nomProf;
    }

    public String getPrenomProf() {
        return prenomProf;
    }

    public void setPrenomProf(String prenomProf) {
        this.prenomProf = prenomProf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfDTO profDTO = (ProfDTO) o;
        if (profDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfDTO{" +
            "id=" + getId() +
            ", nomProf='" + getNomProf() + "'" +
            ", prenomProf='" + getPrenomProf() + "'" +
            "}";
    }
}
