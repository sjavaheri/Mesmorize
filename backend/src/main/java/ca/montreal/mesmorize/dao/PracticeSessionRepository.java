package ca.montreal.mesmorize.dao;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.PracticeSession;

/**
 * Repository for PracticeSession
 */
public interface PracticeSessionRepository extends CrudRepository<PracticeSession, String> {

    
}
