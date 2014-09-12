package com.spider.thread;

import java.util.Set;

import org.htmlparser.filters.LinkStringFilter;

import com.spider.clawer.MyClawer;
import com.spider.download.Downloader;
import com.spider.util.HtmlParserTool;

public class CrawlerThread implements Runnable {

	@Override
	public void run() {

		while(true){
			crawl();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	
	private  synchronized static void crawl(){
		
		String cur = null;
		if ((cur  = MyClawer.queue.pop()) != null) {
			
			if (!MyClawer.visitedSet.contains(cur)){
				
				Downloader.download(cur, "g:/clawer/");
				
				
				MyClawer.visitedSet.add(cur);
				System.out.println(Thread.currentThread().getName()+" visited URL:"+cur);

				// 获取cur 页面下的的url
				Set<String> set = HtmlParserTool.extracLinks(cur,
						new LinkStringFilter("http://"));

				for (String string : set) {

					while ((string.charAt(string.length() - 1)) == '/') { // 去末尾 '/'

						string = string.substring(0, string.length() - 1);
					}

					MyClawer.queue.add(string);

				}
			}
			

		}else System.out.println("end crawl");
	}
	
	
	
	 
}
