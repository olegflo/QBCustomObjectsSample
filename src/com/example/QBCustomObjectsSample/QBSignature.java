package com.example.QBCustomObjectsSample;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

public class QBSignature {
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private String nonce;
	private String timestamp;
	private String signature;
    private String message;

	QBSignature(int appId, String authKey, String authSecret) {
        nonce = String.valueOf(new Random().nextInt(10000));
		timestamp = new Long(System.currentTimeMillis() / 1000).toString();
		message = "app_id=" + appId + "&auth_key=" + authKey + "&nonce="
				+ nonce + "&timestamp=" + timestamp;
		signature = generateSignature(message, authSecret);
	}

	private String generateSignature(String message, String secret) {
		String signature = new String();
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			SecretKeySpec key = new SecretKeySpec(secret.getBytes(), HMAC_SHA1_ALGORITHM);

			mac.init(key);

			byte[] digest = mac.doFinal(message.getBytes());

			StringBuilder sb = new StringBuilder(digest.length * 2);
			String s;
			for (byte b : digest) {
				s = Integer.toHexString(0xFF & b);
				if (s.length() == 1) {
					sb.append('0');
				}

				sb.append(s);
			}

			signature = sb.toString();
		} catch (Exception e) {
			e.printStackTrace(); // TODO
		}

		return signature;
	}

	public String getNonce() {
		return nonce;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getSignature() {
		return signature;
	}

    public String getMessage() {
        return message;
    }
}