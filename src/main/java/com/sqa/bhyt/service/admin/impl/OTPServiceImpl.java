package com.sqa.bhyt.service.admin.impl;

import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.otp.OTPInfoRequest;
import com.sqa.bhyt.dto.request.otp.VerifyOTPRequest;
import com.sqa.bhyt.dto.response.otp.OTPInfoResponse;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.OTPService;


@PropertySource("classpath:application.properties")
@Service
public class OTPServiceImpl implements OTPService {

	// src: https://www.rfc-editor.org/rfc/rfc6238#section-1.2
	private static final Logger logger = LoggerFactory.getLogger(OTPServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	Environment env;

	@Value("${expiredMins}")
	private String expiredMins;
	
	@Value("${returnDigit}")
	private String returnDigitProperty;
	
	@Value("${sha256}")
	private String sha256Algorithm;
	
	private static final int[] DIGITS_POWER = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };
	private static final int MILISECOND_BUFFER = 50;
	
//	private com.google.common.cache.LoadingCache<String, String> otpCache;

	@Override
	public ServiceResponse<OTPInfoResponse> generateOTP(OTPInfoRequest request) {
		
//		otpCache = CacheBuilder.newBuilder()
//				.expireAfterWrite(Long.parseLong(expiredMins), TimeUnit.MINUTES)
//				.build(new CacheLoader<String, String>() {
//					public String load(String key) {
//						return "";
//					}
//				});
		
		try {

			String secret = (request.getUsername() + request.getTransactionId());
			String otp = null;
			
			try {
				TimeZone utc = TimeZone.getTimeZone("UTC");
				Calendar currentDateTime = Calendar.getInstance(utc);
				long movingFactor = currentDateTime.getTimeInMillis();
				String returnDegit = returnDigitProperty;
				String sha256 = sha256Algorithm;

				System.out.println("currentDatetime:" + currentDateTime);
				System.out.println("movingFactor:" + movingFactor);
				
				otp = generateTOTP(secret, Long.toString(movingFactor), returnDegit, sha256);

//				otpCache.put(secret, otp);
				
				OTPInfoResponse response = new OTPInfoResponse();
				response.setOtp(otp);
				response.setExpiredMins(Integer.parseInt(expiredMins));
				response.setTotpSize(Integer.parseInt(returnDegit));
				response.setTransactionId(request.getTransactionId());
				
				return new ServiceResponse<OTPInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return new ServiceResponse<OTPInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<OTPInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	
	
	private byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) {
		try {
			Mac hmac;
			hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}

	private byte[] hexStr2Bytes(String hex) {
		// Adding one byte to get the right conversion
		// Values starting with "0" can be converted
		try {
//			byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
			byte[] bArray = hex.getBytes();
			// Copy all the REAL bytes, not the "first"
			byte[] ret = new byte[bArray.length - 1];
			for (int i = 0; i < ret.length; i++)
				ret[i] = bArray[i + 1];
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

//	private String generateTOTP(String key, String time, String returnDigits) {
//		return generateTOTP(key, time, returnDigits, "HmacSHA1");
//	}
//	
//	private String generateTOTP256(String key, String time, String returnDigits) {
//		return generateTOTP(key, time, returnDigits, "HmacSHA256");
//	}
//	
//	private String generateTOTP512(String key, String time, String returnDigits) {
//		return generateTOTP(key, time, returnDigits, "HmacSHA512");
//	}

	private String generateTOTP(String key, String time, String returnDigits, String crypto) {
		int codeDigits = Integer.decode(returnDigits).intValue();
		String result = null;

		// Using the counter
		// First 8 bytes are for the movingFactor
		// Compliant with base RFC 4226 (HOTP)
		while (time.length() < 16)
			time = "0" + time;

		// Get the HEX in a Byte[]
		byte[] msg = hexStr2Bytes(time);
		byte[] k = hexStr2Bytes(key);

		byte[] hash = hmac_sha(crypto, k, msg);

		// put selected bytes into result int
		int offset = hash[hash.length - 1] & 0xf;

		int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)
				| ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

		int otp = binary % DIGITS_POWER[codeDigits];

		result = Integer.toString(otp);
		while (result.length() < codeDigits) {
			result = "0" + result;
		}
		return result;
	}

	@Override
	public ServiceResponse<Boolean> verifyOTP(VerifyOTPRequest request) {
		
		if(!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getTransactionId()) || 
				!StringUtils.hasText(request.getSubmittedOtp())) {
			return new ServiceResponse<Boolean>(MessageCode.VERIFY_INFO_INVALID, MessageCode.VERIFY_INFO_INVALID_MESSAGE, false);
		}
		
		try {
			
			String secret = (request.getUsername() + request.getTransactionId());
			String otp = null;
			try {
				
				TimeZone utc = TimeZone.getTimeZone("UTC");
				Calendar currentDateTime = Calendar.getInstance(utc);
				
				long movingFactor = currentDateTime.getTimeInMillis();
				
				String returnDegit = returnDigitProperty;
				String sha256 = sha256Algorithm;

				otp = generateTOTP(secret, Long.toString(movingFactor), returnDegit, sha256);

				if(otp.equals(request.getSubmittedOtp())) {
					return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
				}
				
				long timeInMillis = Long.parseLong(expiredMins) * 60 * 1000 + MILISECOND_BUFFER;
				
				for (int i = 1; i < timeInMillis; i++) {
					movingFactor--;
					otp = generateTOTP(secret, Long.toString(movingFactor), returnDegit, sha256);
					
					if (otp.equals(request.getSubmittedOtp()))
						return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
					System.out.println("otp: " + otp);
				}
				
				return new ServiceResponse<Boolean>(MessageCode.VALIDATE_TIME_HAS_EXPIRED, MessageCode.VALIDATE_TIME_HAS_EXPIRED_MESSAGE, false);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
		
	}

//	@Override
//	public ServiceResponse<Boolean> clearOTP(ClearOTPRequest request) {
//		
//		try {
//			if(!StringUtils.hasText(request.getSecret())) {
//				return new ServiceResponse<Boolean>(MessageCode.SECRET_INVALID, MessageCode.SECRET_INVALID_MESSAGE, false);
//			}
//			
//			otpCache.invalidate(request.getSecret());
//			
//			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
//			
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
//		}
//	}


}
