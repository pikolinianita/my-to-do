
package pl.lcc.todo.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author piko
 */
@Service
public class ReposUtils {

    @PersistenceContext
    private EntityManager em;

    String dumpDB(String path){
         return em.createNativeQuery("SCRIPT TO '" + path + "'").getResultList().toString();
    }
    
}
