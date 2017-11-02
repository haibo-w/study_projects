package com.test.study.baidugis;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Application {

	static String debug = "";
	public static void main(String[] args) {

		if(args!= null && args.length > 0){
			debug = args[0];
			System.out.println("read:"+args[0] + " debug:"+debug);
		}
		
		//ApplicationContext applicationContext = new FileSystemXmlApplicationContext("config/applicationContext-db.xml");
		//Application app = applicationContext.getBean(Application.class);
		//app.updateLocation();
		 String[] gis = getGis("浦东商场上海川沙现代店");
		 System.out.println(gis[0]+" - "+gis[1]);
	}

	/**
	 * http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-placeapi
	 * 
	 * @param address
	 * @return [x,y]经度值 ,纬度值
	 * @author Haibo-W 2016年1月8日 下午4:42:58
	 */
	public static String[] getGis(String address) {
		// http://api.map.baidu.com/api?v=1.5&ak=DC14b9fa8f4a619d7e16c793fb7cbbeb&callback
		// http://api.map.baidu.com/place/v2/search?q=饭店&region=北京&output=json&ak=E4805d16520de693a3fe707cdc962045
		// String urlStr = "q=中江路879号6号楼&region=上海&output=json&ak=DC14b9fa8f4a619d7e16c793fb7cbbeb";
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 4000);
		try {
			String url = "http://api.map.baidu.com/place/v2/search?q=" + URLEncoder
					.encode(address, "UTF-8") + "&region=" + URLEncoder
							.encode("上海", "UTF-8") + "&output=json&ak=DC14b9fa8f4a619d7e16c793fb7cbbeb";
			HttpGet get = new HttpGet(url);
			HttpResponse httpResponse = client.execute(get);
			String string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			// System.out.println("response as : " + string);
			client.getConnectionManager().shutdown();
			if (!StringUtils.isBlank(string)) {
				JSONObject object = JSONObject.fromObject(string);
				int status = object.getInt("status");
				if (status == 0) {// 0 成功
					JSONArray jsonArray = object.getJSONArray("results");
					if (jsonArray.size() > 0) {
						JSONObject obj = JSONObject.fromObject(jsonArray.get(0));
						JSONObject loc = obj.getJSONObject("location");
						String lng = loc.getString("lng");// 经度值
						String lat = loc.getString("lat");// 纬度值
						System.out.println("addr[" + address + "]: " + lng + " " + lat);
						return new String[] { lng, lat };
					}
				}
			}
		} catch (Exception e) {
			System.out.println("addr[" + address + "]: get null");
			System.out.println(e.getMessage());
		}
		return null;

	}
}
