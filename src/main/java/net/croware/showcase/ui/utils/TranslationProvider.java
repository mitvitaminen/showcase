package net.croware.showcase.ui.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.vaadin.flow.i18n.I18NProvider;

import net.croware.showcase.app.util.ShowcaseConstants;

/**
 * This class provides the translations from the resource files.
 *
 * @author christian
 *
 */
@Component
public class TranslationProvider implements I18NProvider {
    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Locale> getProvidedLocales() {
        return Collections.unmodifiableList(Arrays.asList(Locale.GERMANY, Locale.US));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        final ResourceBundle bundle = ResourceBundle.getBundle(ShowcaseConstants.RESOURCEBUNDLE_NAME, locale);

        String value = String.format(bundle.getString(key));
        if (value.length() == 0) {
            value = "missingKey: <" + key + ">";
        }
        return value.trim();
    }

}
