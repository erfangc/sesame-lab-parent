package com.erfangc.sesamelab.shared.repositories;

import com.erfangc.sesamelab.shared.Corpus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
public interface CorpusRepository extends JpaRepository<Corpus, Long> {
}
