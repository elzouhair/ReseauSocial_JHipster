package com.elkili.mypackage.service.mapper;

import com.elkili.mypackage.domain.*;
import com.elkili.mypackage.service.dto.ProfDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prof} and its DTO {@link ProfDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfMapper extends EntityMapper<ProfDTO, Prof> {



    default Prof fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prof prof = new Prof();
        prof.setId(id);
        return prof;
    }
}
