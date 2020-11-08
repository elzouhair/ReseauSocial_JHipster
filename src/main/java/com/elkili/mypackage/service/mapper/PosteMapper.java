package com.elkili.mypackage.service.mapper;

import com.elkili.mypackage.domain.*;
import com.elkili.mypackage.service.dto.PosteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Poste} and its DTO {@link PosteDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PosteMapper extends EntityMapper<PosteDTO, Poste> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PosteDTO toDto(Poste poste);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "commentaires", ignore = true)
    @Mapping(target = "removeCommentaire", ignore = true)
    Poste toEntity(PosteDTO posteDTO);

    default Poste fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poste poste = new Poste();
        poste.setId(id);
        return poste;
    }
}
