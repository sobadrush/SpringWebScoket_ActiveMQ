package com.ctbc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chatController")
public class ChatController {

	@Autowired
	private SimpMessagingTemplate msgTemplate; // Spring會自行注入，不需額外配置

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	private static String STATUS;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH 時 MM 分 ss 秒");
	
	@RequestMapping(value = "/broadcast", method = RequestMethod.GET) // http://localhost:8080/SpringWebScoket_ActiveMQ/test
	@ResponseBody
	public void broadcast(@RequestParam("status") String status) {
		System.out.println("========= 【 進入chating() 】, status = " + status + " =========");
		
		STATUS = status;
		
		this.taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					
					if ("stop".equals(STATUS)) {
						break;
					}
					
					System.out.println("Thread runing... " + sdf.format(new Date(System.currentTimeMillis())));
					msgTemplate.convertAndSend("/QUEUE_fuck/greetings", "廣播(convertAndSend QUEUE_fuck)：：：你好!!!!!"); // 【發訊息到 queue 中】
					msgTemplate.convertAndSend("/topicQQQ/greetingsQQ", "廣播(convertAndSend topicQQQ)：：：你好!!!!!");   // 【發訊息到 queue 中】※topicQQQ會變成Queue，因為topic似乎是關鍵字
					msgTemplate.convertAndSend("/topic/greetingsGG", "廣播(convertAndSend topic)：：：你好!!!!!");         // 【發訊息到 topic 中】※topic似乎是關鍵字
					
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});			
	}

	@MessageMapping("/hello")
//	@SendTo("/QUEUE_fuck/greetings")    //  【發訊息到 queue 中】
	@SendTo("/topic/greetingsGG")      //  【發訊息到 topic 中】※topic似乎是關鍵字
//	@SendTo("/topicQQQ/greetingsGG")    //  【發訊息到 topicQQQ ，在ActiveMQ中似乎會變成Queue】※topic似乎是關鍵字
	@ResponseBody
	public String greeting(String messageIncoming) throws Exception {
		System.out.println("========= 【 進入greeting() 】 =========");
		System.out.println("messageIncoming = " + messageIncoming);
		
		JSONObject jsonObject = new JSONObject(messageIncoming);
		String userName = jsonObject.getString("userName");
		
		Thread.sleep(500); // simulated delay  
		return "Hello, " + userName + " !!";
	}
}
