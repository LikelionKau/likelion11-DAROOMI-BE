package com.likelion.daroomi.nuroomi.config;

import com.likelion.daroomi.nuroomi.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//TODO JWT는 accessToekn만 발행했는데 next때는 refreshToken도 구현하는 걸로
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull FilterChain chain)
        throws IOException, ServletException {

        //왠지 모르겠는데 /** 안됨...
        List<String> list = Arrays.asList(
            "/consultantee/login",
            "/consultantee/create",
            "/consultantee/getUserName",
            "/consultantee/modifyUser",
            "/consultant/login",
            "/consultant/create",
            "/consultantee/changePassword",
            "/consultantee/logout",
            "/consulting/waiting",
            "/consulting/start/*",
            "/consulting/end/*"
        );

        if (list.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try {
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = TokenUtils.getTokenFromHeader(header);
                if (TokenUtils.isValidToken(token)) {
                    String userId = TokenUtils.getUserIdFromToken(token);
                    if (userId != null && !userId.equalsIgnoreCase("")) {
                        chain.doFilter(request, response);
                    } else {
                        throw new UserNotFoundException();
                    }
                } else {
                    throw new UserNotFoundException();
                }
            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonResponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    private JSONObject jsonResponseWrapper(Exception e) {

        String resultMsg = "에러 발생";

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("code", "9999");
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason", e.getMessage());
        JSONObject jsonObject = new JSONObject(jsonMap);
        logger.error(resultMsg, e);
        return jsonObject;
    }
}