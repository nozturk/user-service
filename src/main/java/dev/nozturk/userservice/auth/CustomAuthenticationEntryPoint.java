package dev.nozturk.userservice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.nozturk.userservice.exception.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException e) throws IOException {
       ApiError error = ApiError.builder().status(HttpStatus.UNAUTHORIZED.value()).message("User is not authorized").build();

       try {
    	   ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	   String json = ow.writeValueAsString(error);
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.setContentType(MediaType.APPLICATION_JSON_VALUE);
           response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
           response.getWriter().write(json);
       } catch (Exception e1) {
          log.error("Error in AuthenticationEntryPoint");
       }

   }
}