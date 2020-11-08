package com.elkili.mypackage.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.elkili.mypackage.domain.Commentaire} entity.
 */
public class CommentaireDTO implements Serializable {

    private Long id;

    private String commentaire;


    private Long userId;

    private String userLogin;

    private Long posteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

    public Long getPosteId() {
        return posteId;
    }

    public void setPosteId(Long posteId) {
        this.posteId = posteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentaireDTO commentaireDTO = (CommentaireDTO) o;
        if (commentaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentaireDTO{" +
            "id=" + getId() +
            ", commentaire='" + getCommentaire() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", poste=" + getPosteId() +
            "}";
    }
}
