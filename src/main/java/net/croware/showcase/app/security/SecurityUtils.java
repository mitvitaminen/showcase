package net.croware.showcase.app.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;

import net.croware.showcase.backend.data.User;
import net.croware.showcase.ui.views.LoginView;

/**
 * SecurityUtils takes care of all such static operations that have to do with
 * security and querying rights from different beans of the UI.
 *
 */
public final class SecurityUtils {

    /**
     * Tests if a user is granted access to a page of the application.
     *
     * @param securedClass
     * @return true if the user has access granted
     */
    public static boolean isAccessGranted(Class<?> securedClass) {
        final boolean publicView = LoginView.class.equals(securedClass);
        // Always allow access to public views
        if (publicView) {
            return true;
        }

        final Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();

        // All other views require authentication
        if (!isUserLoggedIn(userAuthentication)) {
            return false;
        }

        // Allow if no roles are required.
        final Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
        if (secured == null) {
            return true;
        }

        final List<String> allowedRoles = Arrays.asList(secured.value());

        return userAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .anyMatch(allowedRoles::contains);
    }

    /**
     * Tests if some user is authenticated. As Spring Security always will create an
     * {@link AnonymousAuthenticationToken} we have to ignore those tokens
     * explicitly.
     */
    public static boolean isUserLoggedIn() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return isUserLoggedIn(auth);
    }

    /**
     * Tests if some user is authenticated. As Spring Security always will create an
     * {@link AnonymousAuthenticationToken} we have to ignore those tokens
     * explicitly.
     *
     * @param authetication
     * @return true if user is logged in
     */
    public static boolean isUserLoggedIn(Authentication auth) {
        return auth != null && !(auth instanceof AnonymousAuthenticationToken)
                && auth.isAuthenticated();
    }

    /**
     * Tests if some user is deactivated or has account/credentials expired.
     *
     * @param user
     * @return true if user is NOT deactivated
     */
    public static boolean isUserNonDeactivated(User user) {
        if (user != null && isNonLocked(user) && isCredentialsNonExpired(user) && isAccountNonExpired(user)) {
            return true;
        }
        return false;
    }

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param request {@link HttpServletRequest}
     * @return true if is an internal framework request. False otherwise.
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    private static boolean isAccountNonExpired(User user) {
        return user.isAccountNonExpired();
    }

    private static boolean isCredentialsNonExpired(User user) {
        return user.isCredentialsNonExpired();
    }

    private static boolean isNonLocked(User user) {
        return user.isAccountNonLocked();
    }

    private SecurityUtils() {
        // Util methods only
    }

}