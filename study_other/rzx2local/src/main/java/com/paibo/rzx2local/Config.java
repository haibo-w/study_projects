/**
 * Project Name:com.paibo.rzx2local <br/>
 * Date:2014年5月5日下午4:18:45 <br/>
 */
package com.paibo.rzx2local;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paibo.rzx2local.search.TMFileTools;

/**
 * ClassName: Config <br/>
 * Function: . <br/>
 * Reason: . <br/>
 * date: 2014年5月5日 下午4:18:45 <br/>
 *
 * @author Haibo-W
 * @version 
 * @since JDK 1.6
 */
public class Config {
    
    private static final Logger logger = LoggerFactory.getLogger(TMFileTools.class);

    public static  String TEMP_DIR;
    
    public static  Date START_DATE;
    
    public static Date END_DATE;
    
    public static String SCAN_BASE_PATH = "";
    
    public static String LUCENE_BATH_PATH = "";
    
  
    
    static{
        Properties p = new Properties();
        try {
            File f = new File(System.getProperty("user.dir")+"/config/police.properties");
            p.load(new FileInputStream(f));
            
            START_DATE = TMFileTools.dateFormat.parse(p.getProperty("startDate").trim());
            
            END_DATE= TMFileTools.dateFormat.parse(p.getProperty("endDate").trim());
            
            TEMP_DIR = System.getProperty("user.dir","")+"/temp";
            File t = new File(TEMP_DIR);
            if(!t.exists()){
                t.mkdirs();
            }
            
            SCAN_BASE_PATH = p.getProperty("scanBasePath","");
            
            LUCENE_BATH_PATH = p.getProperty("luceneBasePath","/opt/lucene/LuceneContent/indexes");
            
           
            

        } catch (Exception e1) {
            logger.error("",e1);
        }
         
    }
    
    public enum IndexType{
        CHAT,GROUP;
    }
}
