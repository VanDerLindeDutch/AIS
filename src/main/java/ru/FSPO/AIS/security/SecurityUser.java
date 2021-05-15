package ru.FSPO.AIS.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.FSPO.AIS.newmodels.Role;
import ru.FSPO.AIS.newmodels.AbstractUser;

import java.util.Collection;
import java.util.Set;

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final long id;
    private final Role role;
    private final Set<SimpleGrantedAuthority> authorityList;
    private final boolean isActive;

    public SecurityUser(String username, String password, long id, Role role, Set<SimpleGrantedAuthority> authorityList, boolean isActive) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.role = role;
        this.authorityList = authorityList;
        this.isActive = isActive;
    }

    public Role getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(AbstractUser abstractUser) {
        return new SecurityUser(abstractUser.getLogin(),
                abstractUser.getPassword(),
                abstractUser.getId(),
                abstractUser.getRole(), abstractUser.getRole().getAuthorities(),
                true);
    }
}
