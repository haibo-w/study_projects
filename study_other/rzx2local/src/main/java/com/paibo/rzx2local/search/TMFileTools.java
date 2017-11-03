/**
 * Project Name:com.paibo.rzx2local <br/>
 * Date:2014年5月5日下午4:24:09 <br/>
 */
package com.paibo.rzx2local.search;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paibo.rzx2local.Config;

/**
 * ClassName: TMFileTools <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年5月5日 下午4:24:09 <br/>
 * 
 * @author Haibo-W
 * @version
 * @since JDK 1.6
 */
public class TMFileTools {

    private static final Logger logger = LoggerFactory.getLogger(TMFileTools.class);

    public static boolean existTmFile() {
        try {
            File doneTmFile = new File(Config.TEMP_DIR);
            File[] files = doneTmFile.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith("tm");
                }
            });
            return files.length > 0;
        } catch (Exception e) {
            logger.error("=>检查时间文件报错：", e);
        }
        return false;
    }

    private static File readRecentDayTm() {
        File doneTmFile = new File(Config.TEMP_DIR);
        File[] files = doneTmFile.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".tm");
            }
        });
        if (files.length == 0) {
            return null;
        }
        List<File> list = Arrays.asList(files);
        Collections.sort(list, new Comparator<File>() {
            // 20140503.tm
            /**
             * compare:按文件名称排序. <br/>
             * 
             * @author Haibo-W
             */
            @Override
            public int compare(File o1, File o2) {
                if (Integer.valueOf(o1.getName().split("\\.")[0]) < Integer.valueOf(o2.getName().split("\\.")[0])) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        File file = list.get(0);
        return file;
    }

    /**
     * patten:yyyyMMddHHmm
     */
    public static volatile SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    /** patten:yyyyMMdd */
    public static volatile SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyyMMdd");
    /** patten:yyyy-MM-dd */
    public static volatile SimpleDateFormat dateFormatShort2 = new SimpleDateFormat("yyyy-MM-dd");
    
    
    /**
     * 
     * writeTm2File:追加记录到时间日志文件. <br/>
     * 
     * @author Haibo-W
     * @param date
     * @since JDK 1.6
     */
    public static void writeTm2File(Date date) {
        try {
            String shortTm = dateFormatShort.format(date);
            File tm = new File(Config.TEMP_DIR + "/" + shortTm + ".tm");
            if (tm.exists()) {
                FileWriter writer = new FileWriter(tm, true);
                String format = dateFormat.format(date);
                writer.write(System.getProperty("line.separator") + format);
                writer.close();
            } else {
                tm.createNewFile();
                FileWriter writer = new FileWriter(tm, true);
                String format = dateFormat.format(date);
                writer.write(format);
                writer.close();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 
     * readReceneTm:读取文件末行数据. <br/>
     * 
     * @author Haibo-W
     * @return
     * @since JDK 1.6
     */
    public static Date readReceneTm() {
        try {
            File tm = readRecentDayTm();
            if (tm != null && tm.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(tm, "r");
                long length = randomAccessFile.length();

                long pos = length - 1;
                while (pos > 0) {

                    randomAccessFile.seek(pos);
                    byte readByte = randomAccessFile.readByte();
                    if (readByte == '\n') {
                        break;
                    }
                    pos--;
                }
                if (pos != 0)
                    pos++;
                randomAccessFile.seek(pos);
                //
                String line = randomAccessFile.readLine();
                logger.info("=>readLastLine:" + line);
                randomAccessFile.close();

                if (null != line && !line.trim().isEmpty()) {
                    Date date = dateFormat.parse(line);
                    return date;
                }

            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    @Test
    public void test() throws IOException {
    }

//    private boolean found = false;
//    private String encoding;
//
//    private String geestFileEncoding(File paramFile, nsDetector paramnsDetector) {
//        BufferedInputStream localBufferedInputStream = null;
//        try {
//             paramnsDetector.Init(new nsICharsetDetectionObserver() {
//             public void Notify(String paramString) {
//                 //System.out.println(paramString);
////             FileCharsetDetector.access$002(FileCharsetDetector.this, true);
////             FileCharsetDetector.access$102(FileCharsetDetector.this, paramString);
//             }
//             });
//
//            localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
//
//            byte[] arrayOfByte = new byte[1024];
//
//            boolean bool1 = false;
//            boolean bool2 = true;
//            int i;
//            while ((i = localBufferedInputStream.read(arrayOfByte, 0, arrayOfByte.length)) != -1) {
//                if (bool2) {
//                    bool2 = paramnsDetector.isAscii(arrayOfByte, i);
//                }
//
//                if ((!bool2) && (!bool1))
//                    bool1 = paramnsDetector.DoIt(arrayOfByte, i, false);
//            }
//            paramnsDetector.DataEnd();
//
//            if (bool2) {
//                this.encoding = "ASCII";
//                this.found = true;
//            }
//
//            if (!this.found) {
//                String[] arrayOfString = paramnsDetector.getProbableCharsets();
//                if (arrayOfString.length > 0) {
//                    this.encoding = arrayOfString[0];
//                } else {
//                    Object localObject1 = null;
//                    return (String) localObject1;
//                }
//            }
//        } catch (Exception localException) {
//
//        } finally {
//            if(localBufferedInputStream != null){
//                try {
//                    localBufferedInputStream.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return this.encoding;
//    }
    
    

}
