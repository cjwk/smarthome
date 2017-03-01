package com.wk.dahua;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wk.util.MD5Code;

public class OpenApi {
	/**
	 * 随机生成len位数字
	 */
	public static String randNum(int len) {
		StringBuffer sb = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < len; i++) {
			sb.append(rand.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 取得当前时间数，以秒为单位
	 * 
	 * @return
	 */
	public static String newTime() {
		long time = System.currentTimeMillis() / 1000;
		return time + "";
	}

	/**
	 * 将params中的参数排序
	 * 
	 * @param params
	 * @return获得要求的格式字符串
	 */
	public static String paramsSort(JSONObject params) {
		StringBuffer buffer = new StringBuffer();
		LinkedHashMap<String, String> jsonMap = JSON.parseObject(
				params.toJSONString(),
				new TypeReference<LinkedHashMap<String, String>>() {
				});
		for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
			buffer.append(entry.getKey() + ":").append(entry.getValue())
					.append(",");
		}
		return buffer.toString();
	}

	/**
	 * 按要求用MD5处理字符串
	 * 
	 * @param params
	 * @param time
	 *            当前时间
	 * @param nonce
	 *            32位随机数
	 * @param appSecret
	 * @return
	 */
	public static String getSign(JSONObject params, String time, String nonce,
			String appSecret) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(paramsSort(params));
		buffer.append("time:").append(time).append(",");
		buffer.append("nonce:").append(nonce).append(",");
		buffer.append("appSecret:").append(appSecret);
		System.out.println(buffer.toString());
		String md5 = new MD5Code().getMD5ofStr(buffer.toString());
		return md5;
	}

	public static String sendLeCheng(String data){
		String ver = "1.0";
		String id = randNum(2);
		
		String time = newTime();
		String nonce = randNum(32);
		JSONObject objData = JSON.parseObject(data);
		JSONObject obj = new JSONObject();
		String systemData = objData.getString("system");
		String paramsData = objData.getString("params");
		JSONObject system = JSON.parseObject(systemData);
		JSONObject params = JSON.parseObject(paramsData);
		String appSecret = system.getString("appSecret");
		String appId = system.getString("appId");
		String temp = getSign(params, time, nonce, appSecret);
		String sign = temp == null ? "" : temp;
		system.put("ver", ver);
		system.put("sign", sign);
		system.put("time", time);
		system.put("nonce", nonce);
		system.put("appId", appId);
		obj.put("params", params);
		obj.put("system", system);
		obj.put("id", id);
		return obj.toJSONString();
	}
	public static String jsonPost(String url , String json){
		URL connect;  
	    StringBuffer data = new StringBuffer();  
	    try {  
	        connect = new URL(url);  
	        HttpURLConnection connection = (HttpURLConnection)connect.openConnection();  
	        connection.setRequestMethod("POST");  
	        connection.setDoOutput(true);  
	         
	        OutputStreamWriter paramout = new OutputStreamWriter(  
	                connection.getOutputStream(),"UTF-8");  
	        paramout.write(json); 
	        paramout.flush();  
	  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(  
	                connection.getInputStream(), "UTF-8"));  
	        String line;              
	        while ((line = reader.readLine()) != null) {          
	            data.append(line);            
	        }  
	      
	        paramout.close();  
	        reader.close();  
	    } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return data.toString();
	}

	public static void main(String[] args) {
		//String data = "{\"system\":{\"appId\":\"lc6925d08793c3446e\",\"appSecret\":\"ee60e81bd07f407c8855e43b18fc02\"},\"params\":{\"deviceId\":\"2f00debpak02761\",\"token\":\"At_3dded81bfc32497db62006fd914bc506\"}}";
		String data = "{\"system\":{\"appId\":\"lc6925d08793c3446e\",\"appSecret\":\"ee60e81bd07f407c8855e43b18fc02\"},\"params\":{\"phone\":\"13810365800\"}}";
	//	String data = "{\"system\":{\"appId\":\"lc6925d08793c3446e\",\"appSecret\":\"ee60e81bd07f407c8855e43b18fc02\"},\"params\":{\"token\":\"At_efbf397764da46458a9522ff4d1f5310\",\"queryRange\":\"1-100\"}}";
		String msg = jsonPost(
				"https://openapi.lechange.cn/openapi/accessToken",
				sendLeCheng(data));
		System.out.println(msg);
	}
}
