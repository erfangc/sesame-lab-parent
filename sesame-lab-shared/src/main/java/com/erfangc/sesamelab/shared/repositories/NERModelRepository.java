package com.erfangc.sesamelab.shared.repositories;

import com.erfangc.sesamelab.shared.entities.NERModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "nermodels", path = "nermodels")
public interface NERModelRepository extends JpaRepository<NERModel, Long> {
}
