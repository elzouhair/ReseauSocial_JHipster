package com.elkili.mypackage.repository;

import com.elkili.mypackage.domain.Poste;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Poste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PosteRepository extends JpaRepository<Poste, Long> {

    @Query("select poste from Poste poste where poste.user.login = ?#{principal.username}")
    List<Poste> findByUserIsCurrentUser();

}
