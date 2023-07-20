package com.example.homecastfileserver.repositories;
import com.example.homecastfileserver.dao.Source;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourcesRepository extends CrudRepository<Source, Long> {
}
