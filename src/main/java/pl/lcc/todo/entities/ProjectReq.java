
package pl.lcc.todo.entities;

import java.util.Set;

/**
 *
 * @author piko
 */

public record ProjectReq (String name, Set<String> tags, String reward, String icon){}