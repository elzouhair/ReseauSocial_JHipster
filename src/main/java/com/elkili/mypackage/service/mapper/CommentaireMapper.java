package com.elkili.mypackage.service.mapper;

import com.elkili.mypackage.domain.*;
import com.elkili.mypackage.service.dto.CommentaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commentaire} and its DTO {@link CommentaireDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PosteMapper.class})
public interface CommentaireMapper extends EntityMapper<CommentaireDTO, Commentaire> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "poste.id", target = "posteId")
    CommentaireDTO toDto(Commentaire commentaire);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "posteId", target = "poste")
    Commentaire toEntity(CommentaireDTO commentaireDTO);

    default Commentaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commentaire commentaire = new Commentaire();
        commentaire.setId(id);
        return commentaire;
    }
}
