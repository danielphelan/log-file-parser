package ie.credit.suisse.parser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ie.credit.suisse.parser.model.LogEvent;

@Repository
public interface LogFileRepository extends CrudRepository<LogEvent, String> {

}
