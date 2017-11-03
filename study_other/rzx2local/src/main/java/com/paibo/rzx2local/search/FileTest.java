/**
 * Project Name:com.paibo.rzx2local <br/>
 * Date:2014年5月7日下午12:02:09 <br/>
 */
package com.paibo.rzx2local.search;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

/**
 * ClassName: FileTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年5月7日 下午12:02:09 <br/>
 * 
 * @author Haibo-W
 * @version
 * @since JDK 1.6
 */
public class FileTest {

    // 方法 获取文件的编码格式
    public static boolean found;

    public String getCharSetEncoding(File file) throws Exception{

        boolean found = false;
        nsICharsetDetectionObserver ndo = new nsICharsetDetectionObserver() {

            public void Notify(String arg0) {
                FileTest.found = true;
            }
        };
        nsDetector det = new nsDetector();
        /**
         * 初始化nsDetector() lang为一个整数，用以提示语言线索，可以提供的语言线索有以下几个：
         * 
         * 
         * 1. Japanese 2. Chinese 3. Simplified Chinese 4. Traditional Chinese 5. Korean 6. Dont
         * know (默认)
         */

        // nsDetector det=new nsDetector(lang);
        det.Init(ndo);
        @SuppressWarnings("resource")
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        byte[] buf = new byte[1024];

        boolean done = false;

        boolean isAscii = true;

        int length = 0;

        while ((length = bis.read(buf)) != -1){
            if (isAscii){
                isAscii = det.isAscii(buf, length);
            }
            if (!isAscii && !done){
                done = det.DoIt(buf, length, false);
            }
        }
        det.DataEnd();
        if (isAscii){
            found = true;
            return "ASCII";
        }
        if (!found){

            String pro[] = det.getProbableCharsets();// 获取可能的编码格式

            if (pro.length > 0){
                return pro[0];// 取第一个
            }
        }
        return null;
    }
    @Test
    public void test() throws Exception{
        File f = new File("D:\\opt\\lucene\\LuceneContent\\rzx\\2014\\5\\3\\21\\0\\0504_20140503200453_W33424010284_505896249_304481402_192.168.1.18.GCHAT");
        String encoding = getCharSetEncoding(f);
        System.out.println(encoding);
    }
}
