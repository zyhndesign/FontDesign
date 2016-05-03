package com.cidic.fontdesign.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PinWallHttpClient {

	public static Map<Integer,String> getPinWallDataById(int id) throws ConnectTimeoutException,ClientProtocolException,IOException{ 
		
		String SAMPLE_URL = "http://design.hnu.edu.cn/pinwall/api/topics/"+id+"/artifacts?noCache=1460425238774&count=3&last_id=0"; 
		Map<Integer,String> urlMap = new HashMap<Integer,String>();
		CloseableHttpClient client = HttpClientBuilder.create().build();  
	    RequestConfig requestConfig = RequestConfig.custom()  
	    .setConnectionRequestTimeout(5000).setConnectTimeout(5000)  
	    .setSocketTimeout(5000).build();  
	    HttpGet request = new HttpGet(SAMPLE_URL);  
	    request.setConfig(requestConfig);  
	    CloseableHttpResponse response;
		
		response = client.execute(request);
		HttpEntity entity = response.getEntity();
		urlMap = analysisJsonData(EntityUtils.toString(entity));
		
		return urlMap;
	}  
	
	public static Map<Integer,String> analysisJsonData(String jsonString){
		Map<Integer,String> urlMap = new HashMap<Integer,String>();
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray artifacts = jsonObject.getJSONArray("artifacts");
		for (int i = 0; i < artifacts.size(); i++){
			JSONObject tempObject = artifacts.getJSONObject(i);
			JSONObject artifact = tempObject.getJSONObject("artifact");
			System.out.println(artifact.get("profile_image"));
			urlMap.put(artifact.getInt("id"), artifact.getString("profile_image"));
		}
		return urlMap;
	}
	
}
