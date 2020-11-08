package com.elkili.mypackage.service.mapper;

import com.elkili.mypackage.domain.*;
import com.elkili.mypackage.service.dto.EleveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eleve} and its DTO {@link EleveDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EleveMapper extends EntityMapper<EleveDTO, Eleve> {



    default Eleve fromId(Long id) {
        if (id == null) {
            return null;
        }
        Eleve eleve = new Eleve();
        eleve.setId(id);
        return eleve;
    }
}
