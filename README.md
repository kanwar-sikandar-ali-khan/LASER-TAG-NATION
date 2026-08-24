Libraries:

/////Signup:
import org.springframework.security=>crypto.bcrypt.BCryptPasswordEncoder=> user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); => For Hashing Not Encoding qk BTS Me ye Hashing krta h or hame Return krta h Hashpassword which irreversible

/////Login:
import org.springframework.security=>authentication.AuthenticationManager => extend WebSecurityConfigurerAdapter => (AuthenticationManager internally password decode kr k check krleta iske liye hame alg se decode nahi krna prta or authnticate return krdeta .

After login Token Create:
import io.jsonwebtoken=> call .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()=>   claim,Jwts,SignatureAlgorithm  => For TOken Creation.

/////////On Each Request:
import org.springframework.web.filter=> extend OncePerRequestFilter;=> Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody(); = For Extracting Data From Token

import org.springframework.security=>@Override
    protected void configure(HttpSecurity http) throws Exception => extend WebSecurityConfigurerAdapter => for Authorization purpose.
Note:
WebSecurityConfigurerAdapter khud har request par nahi chalta.
Uska configure(HttpSecurity) method application startup par ek baar chalta hai aur security rules setup karta hai.
Uske configure kiye hue filters (OncePerRequestFilter, etc.) har request par chalte hain.
SimpleCorsFilter.doFilter() bhi har request par chalta hai.





