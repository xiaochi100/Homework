package Tresure;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImagCopy {

    // 爬取百度贴吧的图片
    public static void main(String[] args) throws IOException {
        String url = "http://www.tooopen.com/view/1439719.html";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

        // 1.7
        /*BufferedReader reader = null;
        try {
            // 这个位置出现异常
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        } catch (Exception e) {

        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {

                }
            }
        }*/
        ExecutorService service = Executors.newFixedThreadPool(8);
        String html = fetch(conn);
        Matcher matcher = PATTERN.matcher(html);
        while (matcher.find()) {
            // 获取图片的路径
            String imageURL = matcher.group(1);
            System.out.println(imageURL);
            service.submit( ()-> {
                try {
                    download(imageURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void download(String imageURL) throws IOException {
        HttpURLConnection c = (HttpURLConnection) new URL(imageURL).openConnection();
        // https://imgsa.baidu.com/forum/w%3D580/sign=43e292947c1ed21b79c92eed9d6fddae/6bfab2fb43166d228b3c16f2472309f79052d20a.jpg
        // 图片名
        String image = imageURL.substring(imageURL.lastIndexOf("/") + 1);
        try (
                InputStream in = c.getInputStream();
                OutputStream out = new FileOutputStream("D:\\imagecopy1\\" + image)
        ) {
            byte[] bytes = new byte[1024 * 1024];
            while (true) {
                int len = in.read(bytes);
                if (len == -1) {
                    break;
                }
                out.write(bytes, 0, len);
            }
        }
    }

//    static final Pattern PATTERN = Pattern.compile("<a class=\"pic\".*? <img src=\"(.*?)\"");
//
   static final Pattern PATTERN = Pattern.compile("<.*? class=\".*?\".*? <img src=\"(.*?)\"");
    private static String fetch(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder(1024*1024);
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))
        ) {
            while(true) {
                String line = reader.readLine();
                if(line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch(IOException e) {

        }
        return sb.toString();
    }

}
