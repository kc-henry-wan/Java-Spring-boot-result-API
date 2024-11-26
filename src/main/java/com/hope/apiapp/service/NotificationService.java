package com.hope.apiapp.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

//	private final AmazonSNS snsClient;
//
//	@Value("${aws.region}")
//	private String awsRegion;
//
//	String topicArn;
//
//	@Autowired
//	private DeviceTokenRepository deviceTokenRepository;
//
//	public NotificationService() {
//		this.snsClient = AmazonSNSClientBuilder.standard().withRegion(awsRegion).build();
//		this.topicArn = snsClient.createTopic("YourTopicName").getTopicArn();
//
//	}
//
//	public void sendNotificationToUser(String userId, String message) {
//		DeviceToken deviceToken = deviceTokenRepository.findByUserId(userId);
//
//		if (deviceToken != null) {
//			PublishRequest publishRequest = new PublishRequest(deviceToken.getToken(), message);
//			snsClient.publish(publishRequest);
//		} else {
//			throw new ResourceNotFoundException("RBF-D001- " + userId);
//		}
//	}
//
//	public void sendBulkNotification(String message) {
//		PublishRequest publishRequest = new PublishRequest(topicArn, message);
//		snsClient.publish(publishRequest);
//	}
}
