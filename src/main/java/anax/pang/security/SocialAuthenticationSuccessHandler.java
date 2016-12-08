package anax.pang.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.util.UriUtils;

public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public static final String REDIRECT_PATH_BASE = "/#/login";
    public static final String FIELD_TOKEN = "access_token";
    public static final String FIELD_EXPIRATION_SECS = "expires_in";

    //private final Logger log = LoggerFactory.getLogger(getClass());
    private final AuthorizationServerTokenServices authTokenServices;
    private final String localClientId;

    public SocialAuthenticationSuccessHandler(AuthorizationServerTokenServices authTokenServices, String localClientId){
        this.authTokenServices = authTokenServices;
        this.localClientId = localClientId;
    }

    
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
        //log.debug("Social user authenticated: " + authentication.getPrincipal() + ", generating and sending local auth");
        OAuth2AccessToken oauth2Token = authTokenServices.createAccessToken(convertAuthentication(authentication)); //Automatically checks validity
        String redirectUrl = new StringBuilder(REDIRECT_PATH_BASE)
            .append("?").append(FIELD_TOKEN).append("=")
            .append(encode(oauth2Token.getValue()))
            .append("&").append(FIELD_EXPIRATION_SECS).append("=")
            .append(oauth2Token.getExpiresIn())
            .toString();
        //log.debug("Sending redirection to " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    private OAuth2Authentication convertAuthentication(Authentication authentication) {
        OAuth2Request request = new OAuth2Request(null, localClientId, null, true, null,
                null, null, null, null);
        return new OAuth2Authentication(request,
                //Other option: new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "N/A", authorities)
                new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), "N/A")
                );
    }

    private String encode(String in){
        String res = in;
        try {
            res = UriUtils.encode(in, "UTF-8");
        } catch(UnsupportedEncodingException e){
           // log.error("ERROR: unsupported encoding: " + "UT", e);
        }
        return res;
    }
}