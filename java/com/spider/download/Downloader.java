package com.spider.download;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Downloader {

	public static String download(String url, String folder) {

		//System.out.println(url);
		String path = null;
		String suffix = null;// 后缀名
		HttpClient httpClient = new DefaultHttpClient();
		
		
		HttpGet get = new HttpGet(url);
		
		try {
			HttpResponse resp = httpClient.execute(get);
			int statusCode = resp.getStatusLine().getStatusCode();
			if (statusCode == 200) {

				Header contentType = resp.getFirstHeader("Content-Type");
				String type = contentType.getValue();

				HttpEntity entity = resp.getEntity();
				byte[] b = EntityUtils.toByteArray(entity);
				String filename = null;
				if (type.indexOf("text") != -1) {

					if(type.indexOf(";") > 0)
						suffix = type.substring(5, type.indexOf(";"));
					else suffix = type.substring(5);
					
					filename = url.substring(url.indexOf("//") + 2) + "."
								+ suffix;

				} else if (type.indexOf("image") != -1) {

					suffix = type.substring(6);
					filename = url.substring(url.lastIndexOf("/") + 1,
							url.lastIndexOf(".") + 1)
							+ suffix;
				}

				path = FileUtils.writeByte(b, folder + filename);

			}// 若需要转向，则进行转向操作
			else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statusCode == HttpStatus.SC_SEE_OTHER)
					|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				// 读取新的URL 地址
				Header header = resp.getFirstHeader("location");
				if (header != null) {
					String newUrl = header.getValue();
					if (newUrl == null || newUrl.equals("")) {
						newUrl = "/";
					}
					download(newUrl, folder);// 发送请求，做进一步处理……
				}
			} else {
				throw new Exception("错误代码:"+statusCode);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return path;
	}

	public static void main(String[] args) {
		download(
				"http://www.77music.com/yuepuku/63/31510/prev_31510.0.png?t=1404444046",
				"g:/clawer/");

	}

}
