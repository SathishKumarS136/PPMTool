package com.sathish.reactapplication.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sathish.reactapplication.domain.User;
import com.sathish.reactapplication.service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJWTFromRequest(request);
			if(StringUtils.hasText(jwt)&&jwtTokenProvider.validateJWT(jwt)) {
				Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
				User user = customUserDetailsService.loadUserById(userId);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Could not set user authentication in security context"+e);
		}
		
		filterChain.doFilter(request, response);

	}

	public String getJWTFromRequest(HttpServletRequest request) {
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if (StringUtils.hasText(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX))
			return header.substring(7, header.length());
		return null;
	}

}
