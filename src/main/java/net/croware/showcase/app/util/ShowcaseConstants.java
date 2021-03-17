package net.croware.showcase.app.util;

import net.croware.showcase.backend.data.Role;

/**
 * Utility class for holding constants.
 *
 * @author christian
 *
 */
public class ShowcaseConstants {

    public static final long SERIAL_VERSION_UID = 1L;

    // Applications default options TODO read from db
    public static final String CSS_SHARED_FILE = "./styles/shared-styles.css";
//    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 50);
    public static final int NOTIFICATION_DURATION = 30;
    public static final String RESOURCEBUNDLE_NAME = "i18n.i18n";

    // Date patterns
    public static final String DATEPATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    // Router paths
    public static final String ROUTERPATH_ADMINAREA_EDITOR = "adminarea_users_edit";
    public static final String ROUTERPATH_ADMINAREA_USERS = "adminarea_users";
    public static final String ROUTERPATH_DEACTIVATEDUSER = "error_deactivateduser";
    public static final String ROUTERPATH_EDITUSER = "edituser";
    public static final String ROUTERPATH_FRONTPAGE = "frontpage";
    public static final String ROUTERPATH_LOGIN = "login";
    public static final String ROUTERPATH_NOTAUTHORIZED = "error/unauthorized";

    // Spring Securit @Secured
    public static final String SECURED_FOR_ADMIN = Role.Type.ADMIN.toString();
    public static final String SECURED_FOR_USER = Role.Type.USER.toString();

    private ShowcaseConstants() {
        // no intanciation since this is only util class
    }

}
