package ma.est.carrentalnm.dtos;

import ma.est.carrentalnm.entities.Role;
import ma.est.carrentalnm.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class userDto implements UserDetails {
    private User user;
    public userDto(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        Collection<Role> roles = user.getRoles();
            roles.forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role.getName()))
            );
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
