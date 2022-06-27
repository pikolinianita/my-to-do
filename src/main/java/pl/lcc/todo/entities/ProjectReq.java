
package pl.lcc.todo.entities;

import java.util.Set;

/**
 *
 * @author piko
 */
//public class ProjectReq {
//    
//    String name;
//    
//    Set<String> tags;
//    
//    String reward;
//    
//    String icon;
//}

public record ProjectReq (String name, Set<String> tags, String reward, String icon){}