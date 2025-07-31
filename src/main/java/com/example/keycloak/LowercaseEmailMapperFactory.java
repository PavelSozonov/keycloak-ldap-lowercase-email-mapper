package com.example.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.LDAPStorageMapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating LowercaseEmailMapper instances.
 * This factory is responsible for providing metadata about the mapper
 * and creating new instances when requested by Keycloak.
 */
public class LowercaseEmailMapperFactory implements LDAPStorageMapperFactory {

    public static final String PROVIDER_ID = "lowercase-email-mapper";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    /**
     * Returns the display name for this mapper in the Keycloak admin console.
     */
    public String getDisplayType() {
        return "Lowercase Email Mapper";
    }

    /**
     * Returns help text describing what this mapper does.
     */
    public String getHelpText() {
        return "Converts LDAP mail attribute to lowercase during user import to ensure consistent email handling";
    }

    /**
     * Creates a new instance of the LowercaseEmailMapper.
     */
    @Override
    public AbstractLDAPStorageMapper create(KeycloakSession session, ComponentModel model) {
        LDAPStorageProvider ldapProvider = (LDAPStorageProvider) session.getProvider(
            LDAPStorageProvider.class, 
            model.getParentId()
        );
        return new LowercaseEmailMapper(model, ldapProvider);
    }

    /**
     * Returns configuration properties for this mapper.
     * This mapper requires no additional configuration.
     */
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return new ArrayList<>();
    }
}
