//package com.shopcartify.utils;
//
//
//import java.time.Instant;
//
//import static com.shopcartify.utils.Constants.APP_NAME;
//import static org.springframework.security.config.Elements.JWT;
//
//
//public class JwtUtil {
//
//    public static String generateVerificationToken(String email){
//        String token = JWT.create()
//                .withClaim("user", email)
//                .withIssuer(APP_NAME)
//                .withExpiresAt(Instant.now().plusSeconds(3600))
//                .sign(Algorithm.HMAC512("secret"));
//        return token;
//    }
//
//
//    public static String extractEmailFrom(String token){
//        var claim = JWT.decode(token).getClaim("user");
//        return (String) claim.asMap().get("user");
//    }
//    public static String encrypt(String token){
//       return JWT.create()
//               .withClaim("text to encrypt", token)
//               .withIssuer(APP_NAME)
//               .withExpiresAt(Instant.now().plusSeconds(3600*24))
//               .sign(Algorithm.HMAC512("secret key"));
//    }
//    public static String decrypt(String jwtToken){
//       var claim = JWT.decode(jwtToken).getClaim("text to encrypt");
//       return (String) claim.asMap().get("text to encrypt");
//    }
//    public static boolean validateToken(String token){
//        JWTVerifier  verifier = JWT.require(Algorithm.HMAC512("secret key"))
//                .withIssuer(APP_NAME)
//                .withClaimPresence("text to encrypt")
//                .build();
//        return verifier.verify(token)!= null;
//    }
//}
