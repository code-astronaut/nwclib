package io.scits.nwclib.repository;

import io.scits.nwclib.model.WebComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebComponentRepository extends PagingAndSortingRepository<WebComponentEntity, Long>, JpaRepository<WebComponentEntity, Long> {
    @Query("SELECT wce FROM WebComponentEntity wce " +
            "WHERE wce.name =:webComponentName")
    Optional<WebComponentEntity> findByName(@Param("webComponentName") String webComponentName);

    @Query("SELECT CASE WHEN COUNT(wce) > 0 THEN TRUE ELSE FALSE END FROM WebComponentEntity wce " +
            "WHERE wce.name =:webComponentName")
    boolean existsByName(@Param("webComponentName") String webComponentName);
}
