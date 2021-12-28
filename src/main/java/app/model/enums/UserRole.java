package app.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public enum UserRole {
    ANONYMOUS(Collections.unmodifiableSet(EnumSet.of(Permission.READ))),
    USER(Collections.unmodifiableSet(EnumSet.of(Permission.READ))),
    ADMIN(Collections.unmodifiableSet(EnumSet.of(Permission.WRITE)));

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
