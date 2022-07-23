
package pl.lcc.todo.entities;

import java.time.LocalDateTime;

/**
 *
 * @author piko
 */
public record EventReq(String name, LocalDateTime from, LocalDateTime to, String remarks) {

}
