package _00_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	public WebSocketConfig() {
		System.out.println("================= 【WebSocketConfig 起動】 ====================");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/appFuck");

		// 【使用 ActiveMQ 需此設定】
		config.enableStompBrokerRelay("/topic"/*對應activeMQ的topic頁籤(※topic似乎是關鍵字)*/ ,"/QUEUE_fuck" /*對應activeMQ的queue頁籤*/ , "/topicQQQ" /*對應activeMQ的queue頁籤*/)
				.setRelayHost("localhost")
				.setRelayPort(61613)
				.setSystemLogin("admin")
				.setSystemPasscode("admin")
				.setClientLogin("admin")
				.setClientPasscode("admin");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/myStompEndPoint")
				.setAllowedOrigins("*")
				.withSockJS();
	}

	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	// =-=-=-=-=-【若啟動 ActiveMQ.bat 不需此 @Bean】=-=-=-=-=
	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	
//	@Bean(initMethod = "start", destroyMethod = "stop")
//	public BrokerService broker() throws Exception {
//		final BrokerService broker = new BrokerService();
//		broker.addConnector("stomp://localhost:61613");
//
//		broker.setPersistent(false);
//		final ManagementContext managementContext = new ManagementContext();
//		managementContext.setCreateConnector(true);
//		broker.setManagementContext(managementContext);
//
//		return broker;
//	}
	
}
