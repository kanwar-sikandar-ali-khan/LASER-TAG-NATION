Libraries:

io.jsonwebtoken=>claim,Jwts,SignatureAlgorithm

import org.springframework.web.filter=>OncePerRequestFilter;

import org.springframework.security=>
authentication.AuthenticationManager(AuthenticationManager internally password decode/encode kr k check krleta iske liye hame alg se decode/encode nahi krna prta),
crypto.bcrypt.BCryptPasswordEncoder


flow:
Bas yaad rakho:
WebSecurityConfigurerAdapter khud har request par nahi chalta.
Uska configure(HttpSecurity) method application startup par ek baar chalta hai aur security rules setup karta hai.
Uske configure kiye hue filters (OncePerRequestFilter, etc.) har request par chalte hain.
SimpleCorsFilter.doFilter() bhi har request par chalta hai.
