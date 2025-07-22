//package com.learning.gateway.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//
//import javax.crypto.spec.SecretKeySpec;
//
//@Configuration
//public class JwtConfig {
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String secretKey = "f30b9aa316baa495e796b7af9669bcdb65f3d8a6531005a4ec3acd4259eb1c6c1bf2752840a326ec5310648e9727c4dcfbf19bf7a10aa2d578dfa8ede511c64fc87713327c76f76b423d57350aa0672611f5f5c38e34252a0393f407d547b83e820b5c11b5597d0e10766ec6b4b8aee1c317ee588028d6457ba9971be3da88f68d6df19c78e0a88d0fb331ebd6d5b291ec2dc25f7def552bf23d5e3b5139dede2c5440b8d89c2bdbf0dd10b246aae6b3733c59704a4775a2c7865539eb5aaca47488d87fa748dee2b878fb58b0011c513b8861b482f1d79a2854a7e987c77a95d85abc5d23d0d40113442b1399e80e0fbc108b8f0a26a873f9736c95fac03fd1==";
//        return NimbusJwtDecoder.withSecretKey(
//                new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
//        ).build();
//    }
//
//
//}
//
//
