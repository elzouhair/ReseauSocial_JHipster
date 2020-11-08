package com.elkili.mypackage.repository;

import com.elkili.mypackage.domain.Eleve;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Eleve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {

}
