package com.elkili.mypackage.repository;

import com.elkili.mypackage.domain.Commentaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Commentaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    @Query("select commentaire from Commentaire commentaire where commentaire.user.login = ?#{principal.username}")
    List<Commentaire> findByUserIsCurrentUser();

}
