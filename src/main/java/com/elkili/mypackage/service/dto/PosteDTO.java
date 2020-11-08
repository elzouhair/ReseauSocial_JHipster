package com.elkili.mypackage.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.elkili.mypackage.domain.enumeration.Type;

/**
 * A DTO for the {@link com.elkili.mypackage.domain.Poste} entity.
 */
public class PosteDTO implements Serializable {

    private Long id;

    private String description;

    private Type type;

    @Lob
    private byte[] fichier;

    private String fichierContentType;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public String getFichierContentType() {
        return fichierContentType;
    }

    public void setFichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PosteDTO posteDTO = (PosteDTO) o;
        if (posteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), posteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PosteDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
