package ca.montreal.mesmorize.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.PracticeSession;

/**
 * Repository for PracticeSession
 */
public interface PracticeSessionRepository extends CrudRepository<PracticeSession, String> {

   /**
    * Find a practice session by its id
    * @param id
    * @return a practice session
    * @author Shidan Javaheri 
    */
   ArrayList<PracticeSession> findPracticeSessionByItemId(String id); 
}
