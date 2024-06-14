package com.cordernot.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String customerId; // Ajoutez customerId ici, ou tout autre champ supplémentaire dont vous avez besoin
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String customerId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.customerId = customerId;
        this.authorities = authorities;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Autres méthodes de UserDetails à implémenter...

    @Override
    public boolean isEnabled() {
        return true; // Vous pouvez implémenter la logique pour vérifier si le compte est activé
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Vous pouvez implémenter la logique pour vérifier si les credentials ne sont pas expirés
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Vous pouvez implémenter la logique pour vérifier si le compte n'est pas expiré
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Vous pouvez implémenter la logique pour vérifier si le compte n'est pas verrouillé
    }
}