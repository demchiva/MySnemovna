package cz.my.snemovna.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class facilitates localization by retrieving text from property files based on locale.
 */
@Component
@Slf4j
public class Resources {
    private static final String MY_SNEMOVNA_RESOURCES = "MySnemovnaResources";

    /**
     * Get text by key and locale from correct property file of MySnemovnaResources.
     * @param locale      Locale of text. In case of null or unsupported language choose EN.
     * @param propertyKey Key from MySnemovnaResources_*.properties
     * @return Localised text.
     */
    public String getMySnemovnaText(@Nullable final Locale locale,
                                    @NotNull final String propertyKey) {
        return getResourcesText(MY_SNEMOVNA_RESOURCES, locale, propertyKey);
    }

    private String getResourcesText(@NotNull final String resourcesBaseName,
                                    @Nullable final Locale locale,
                                    @NotNull final String propertyKey) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(
                resourcesBaseName,
                locale == null ? Locale.ENGLISH : locale,
                new ResourceBundle.Control() {
                    @Override
                    public Locale getFallbackLocale(String baseName, Locale locale) {
                        return Locale.ENGLISH;
                    }
                }
        );

        try {
            return resourceBundle.getString(propertyKey);
        } catch(Exception ex){
            if (propertyKey.matches("[A-Za-z0-9_.]+")) {
                LOGGER.error("Resource not found: {}", propertyKey);
            }
            return Strings.EMPTY;
        }
    }
}
