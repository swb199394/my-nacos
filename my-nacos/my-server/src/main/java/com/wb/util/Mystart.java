package com.wb.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/25 16:22
 */
public class Mystart {

    public static void main(String[] args) throws Exception {

        //获取接口调用凭证access_token
        String appId = "wx3db637d0b8b785de";//小程序id
        String appKey = "e30010919ec922ba22c5d7b30e32e77d";//小程序密钥
        String token = postToken(appId, appKey);

        //生成二维码
        generateQrCode("E:\\test.png", "pages/index/index", "aa=108&bb=2&cc=3", token);

    }

    public static void generateQrCode(String filePath, String page, String scene, String accessToken) {

        try {

            //调用微信接口生成二维码
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            //这就是你二维码里携带的参数 String型  名称不可变
            paramJson.put("scene", scene);
            //注意该接口传入的是page而不是path
            paramJson.put("page", page);
            //这是设置扫描二维码后跳转的页面
            paramJson.put("width", 200);
            paramJson.put("is_hyaline", true);
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();

            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File(filePath));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("打开地址查看生成的二维码：" + filePath);

    }

    /**
     * 接口调用凭证 access_token
     */
    public static String postToken(String appId, String appKey) throws Exception {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appKey;
        URL url = new URL(requestUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes("");
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in;
        if (requestUrl.contains("nlp")) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        }
        StringBuilder result = new StringBuilder();
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result.append(getLine);
        }
        in.close();
        JSONObject jsonObject = JSONObject.parseObject(result.toString());
        return jsonObject.getString("access_token");
    }
}
