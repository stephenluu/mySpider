package com.spider.clawer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.htmlparser.filters.LinkStringFilter;

import com.spider.download.Downloader;
import com.spider.thread.CrawlerThread;
import com.spider.util.HtmlParserTool;

public class MyClawer {
	
	
	public static Set<String> visitedSet = new HashSet<String>();
	public static LinkedList<String> queue = new LinkedList<String>();
	
	//main 方法入口
	public static void main(String[]args)
	{
		MyClawer crawler = new MyClawer();
		crawler.crawling(new String[]{"http://www.baidu.com"});
	}
	
	
	/**
	* 抓取过程
	* @return
	* @param seeds
	*/
	public void crawling(String[] seeds)
	{ 
		//定义过滤器，提取以 开头的链接
		//LinkStringFilter filter = new LinkStringFilter("http://www.baidu.com");
		
		
		for (String string : seeds) {

			queue.add(string);
		}
		
		Thread t = null;
		for (int i = 0; i < 10; i++) {
			
			t = new Thread(new CrawlerThread());
			t.start();
			System.out.println(t.getName()+" stated.");
		}
			
	
	}
}
