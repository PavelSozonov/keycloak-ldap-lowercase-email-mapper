package com.example.keycloak;

import java.util.Locale;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.keycloak.storage.ldap.idm.query.internal.LDAPQuery;

/**
 * LDAP mapper that converts email addresses to lowercase when importing users from LDAP.
 * This ensures consistent email handling regardless of the case stored in LDAP.
 */
public class LowercaseEmailMapper extends AbstractLDAPStorageMapper {

    public LowercaseEmailMapper(ComponentModel mapperModel, LDAPStorageProvider ldapProvider) {
        super(mapperModel, ldapProvider);
    }

    /**
     * Called when importing a user from LDAP to Keycloak.
     * Converts the email address to lowercase before storing it in Keycloak.
     */
    @Override
    public void onImportUserFromLDAP(LDAPObject ldapUser, UserModel user, RealmModel realm, boolean isCreate) {
        String mail = ldapUser.getAttributeAsString("mail");
        if (mail != null && !mail.isEmpty()) {
            user.setEmail(mail.toLowerCase(Locale.ROOT));
        }
    }

    /**
     * Called when registering a user from Keycloak to LDAP.
     * No action needed for this mapper.
     */
    @Override
    public void onRegisterUserToLDAP(LDAPObject ldapUser, UserModel localUser, RealmModel realm) {
        // No action needed when writing to LDAP
    }

    /**
     * Returns the user model delegate without modification.
     */
    @Override
    public UserModel proxy(LDAPObject ldapUser, UserModel delegate, RealmModel realm) {
        return delegate;
    }

    /**
     * Called before LDAP query execution.
     * No query modifications needed for this mapper.
     */
    @Override  
    public void beforeLDAPQuery(LDAPQuery query) {
        // No LDAP query modifications needed
    }
}
