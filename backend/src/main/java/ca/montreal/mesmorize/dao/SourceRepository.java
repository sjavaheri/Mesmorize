package ca.montreal.mesmorize.dao;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.Source;

/**
 * Repository for Source
 */
public interface SourceRepository extends CrudRepository<Source, String> {


    /**
     * Find source by title
     * @param title the title of the source
     * @return a source
     * @author Shidan Javaheri 
     */
    Source findSourceByTitle(String title);
    
}
