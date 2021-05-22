package chinh.nguyen.demo3.dto.reponse;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
//    private Long id;
    private String token;
    private String type = "Bearer";
    private String name;
//    private String email;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String token, String type, String name, String avatar, Collection<? extends GrantedAuthority> roles) {
//        this.id = id;
        this.token = token;
        this.type = type;
        this.name = name;
//        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }

    public JwtResponse() {
    }

    public JwtResponse(String jwt, String name, Collection<? extends GrantedAuthority> authorities) {
        this.token = jwt;
        this.name = name;
        this.roles = authorities;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}
