package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ConsultanteeRepository consultanteeRepository;

    public UserDetailsServiceImpl(
        ConsultanteeRepository consultanteeRepository) {
        this.consultanteeRepository = consultanteeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Consultantee> user = consultanteeRepository.findByLoginId(loginId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        Consultantee consultantee = user.get();

        // user 테이블에 있는 role 값에 따라 권한 부여
        if (consultantee.getRole().equals("ROLE_ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_CONSULTEE"));
        }
        setSessionData(consultantee, authorities);
        return new org.springframework.security.core.userdetails.User(
            consultantee.getName(),
            consultantee.getPassword(),
            authorities
        );
    }

    private void setSessionData(Consultantee consultantee, List<GrantedAuthority> authorities) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        securityContext.setAuthentication(
            new UsernamePasswordAuthenticationToken(consultantee.getLoginId(),
                consultantee.getPassword(), authorities));
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.
            SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }
}

