/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetracker;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import static java.time.Instant.now;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Hien Long
 */
public class TimeTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Date time;
        time = new Date();
        System.out.println( now());
        System.out.println(time.toString());
       java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
       
        System.out.println(date);
        
    }
    
}
