package com.sqa.bhyt.common.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "1111111111111111111111111111111111111111111111";
	
	public static final String SERIAL = "serial";
	
	@Value("${activateaccount}")
	private String activateValidTimes;
	
	public String generateSerialNumberToken (String serial) {
		String token = null;
		try {
			JWSSigner signer = new MACSigner(generateShareSecret());
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(SERIAL, serial);
			builder.expirationTime(generateExpirationDate());
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			signedJWT.sign(signer);
			token = signedJWT.serialize();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return token;
	}
	
	private byte[] generateShareSecret() {
		byte[] shareSecret = new byte[20];
		shareSecret = SECRET_KEY.getBytes();
		return shareSecret;
	}
	
	private Date generateExpirationDate() {
		int time = Integer.parseInt(activateValidTimes) * 60 * 1000;
		return new Date(System.currentTimeMillis() + time);
	}
	
	public String getSerialNumberFromToken(String token) {
		String serial = null;
		try {
			JWTClaimsSet claimsSet = getClaimsFromToken(token);
			serial = claimsSet.getStringClaim(SERIAL);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return serial;
	}
	
	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return claims;
	}
	
	public int validateTokenSerialNumber(String token) {
		if (token == null || token.trim().length() == 0) {
			return 0;
		}
		String serial = getSerialNumberFromToken(token);
		if (serial == null || serial.isEmpty()) {
			return 0;
		}
		if (isTokenExpired(token)) {
			return 0;
		}
		return 1;
	}
	
	private boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	private Date getExpirationDateFromToken(String token) {
		JWTClaimsSet claimsSet = getClaimsFromToken(token);
		Date expiration = claimsSet.getExpirationTime();
		return expiration;

	}
	
}
