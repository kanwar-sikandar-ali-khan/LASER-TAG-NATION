package com.lasertagnation.config.security;

import com.lasertagnation.model.CustomUserDetail;
import com.lasertagnation.service.impl.MyUserDetailServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailServiceImplementation myUserDetailServiceImplementation;

    ///  this will run on each api request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // store token in variable
                jwt = authorizationHeader.substring(7);

                // store username in variable
                username = jwtUtil.extractUsername(jwt);


                // SecurityContextHolder.getContext().getAuthentication() == null
                             //Prevents re-authentication
//                ✔ Important for performance
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //it has my data customUserDetail
                    CustomUserDetail customUserDetail = myUserDetailServiceImplementation.loadUserByUsername(username);
//                    System.out.println("customUserDetail.getAuthorities=>: " + customUserDetail);
//                    output
//                    ["ROLE_ADMIN", "Dashboard", "Orders", "Customers"]
//
                    if (jwtUtil.validateToken(jwt, customUserDetail)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customUserDetail, null, customUserDetail.getAuthorities());
                            //error here

                        //i add it to You will see exactly what Spring is checking
//                        Add this temporarily in controller:
//                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                        System.out.println(auth.getAuthorities());



                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//                        User is authenticated
//                        Spring Security knows user roles
//                        Spring Security knows permissions
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
//        ExceptionResponseDto exception= new ExceptionResponseDto(HttpStatus.UNAUTHORIZED, LocalDateTime.now().toString(),"Jwt Token is Expired");
            e.printStackTrace();
            response.setStatus(500);
            response.setHeader("Access-Control-Allow-Origin", "*");
//        response.getWriter().write(new Gson().toJson(exception));
            return;
        }
    }

}
