package com.elkili.mypackage.repository;

import com.elkili.mypackage.domain.Prof;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prof entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfRepository extends JpaRepository<Prof, Long> {

}
