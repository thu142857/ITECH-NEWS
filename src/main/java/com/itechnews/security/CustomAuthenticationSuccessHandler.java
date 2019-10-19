package com.itechnews.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

//todo
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    //@Autowired
    //private UserDAO userDAO;

    public CustomAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //super.onAuthenticationSuccess(request, response, authentication);
        //this.setAlwaysUseDefaultTargetUrl(false);

//        System.out.println("handle session");
        //HttpSession session = request.getSession();
        //String username = authentication.getName();
        //User authUser = userDAO.getItemByUsername(username);
        //System.out.println(authUser);
        //session.setAttribute("authUser", authUser);

        /*Set target URL to redirect*/
//        String targetUrl = determineTargetUrl(authentication);
//        String targetUrl = "/admin";
//        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
    protected String determineTargetUrl(Authentication authentication) {
		Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (authorities.contains("ROLE_ADMIN")) {
			return "/admin.htm";
		} else if (authorities.contains("ROLE_USER")) {
			return "/user.htm";
		} else {
			throw new IllegalStateException();
		}
	}

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
