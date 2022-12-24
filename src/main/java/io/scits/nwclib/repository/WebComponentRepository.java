package io.scits.nwclib.repository;

import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.service.WebComponentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebComponentRepository extends PagingAndSortingRepository<WebComponentEntity, Long>, JpaRepository<WebComponentEntity, Long> {
}
