/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;


import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

/**
 *
 * @author piko
 */
@DataJpaTest
@Import(ReposUtils.class)
public class ReposUtilsTest {
    
   @Autowired
   ReposUtils util;
   
   @Autowired EntityManager em;
   
   @Test
   void testSimple(){
      assertThat(util.dumpDB("dumptest.dmp")).isNotEmpty();
   }
    
   @Test
   void someFun(){
      
   }
           
    
}
