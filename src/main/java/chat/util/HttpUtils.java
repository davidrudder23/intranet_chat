package chat.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.message.MessageResource;

public class HttpUtils {
    static Logger logger = LoggerFactory.getLogger(MessageResource.class);

	public static Map<String, Object> getFailureStatus(String message) {
		return getFailureStatus(message, null);
	}
	
	public static Map<String, Object> getFailureStatus(String message, Exception e) {
		Map<String, Object> status = new HashMap<String, Object>();
		status.put ("status", "Failure");
		status.put ("message", message);
		status.put ("date", new Date());
		if (e != null) {
			logger.error("Couldn't transfer file", e);
		}
		return status;
	}
}
