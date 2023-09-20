package com.pacoprojects.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pacoprojects.portfolio.model.enums.converter.UserRoleApplicationConverter;
import com.pacoprojects.portfolio.model.enums.UserRoleApplication;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user-application",
        uniqueConstraints = {@UniqueConstraint(name = "unique_username", columnNames = "username")})
@Entity
public class UserApplication implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", length = 100, nullable = false)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "role", length = 10, nullable = false)
    @Convert(converter = UserRoleApplicationConverter.class)
    @NotNull(message = "Role is mandatory")
    private UserRoleApplication userRoleApplication;

    @Column(name = "enabled", nullable = false)
    @JsonIgnore
    private boolean enabled;

    @Column(name = "locked", nullable = false)
    @JsonIgnore
    private boolean locked;

    @OneToMany(targetEntity = TokenConfirmation.class, mappedBy = "userApplication", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TokenConfirmation> tokenConfirmations = new LinkedHashSet<>();

    @OneToMany(targetEntity = Skill.class, mappedBy = "userApplication", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Skill> skills = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoleApplication.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void addTokenConfirmation(TokenConfirmation tokenConfirmation) {
        tokenConfirmations.add(tokenConfirmation);
        tokenConfirmation.setUserApplication(this);
    }

    public void removeTokenConfirmation(TokenConfirmation tokenConfirmation) {
        tokenConfirmations.remove(tokenConfirmation);
        tokenConfirmation.setUserApplication(null);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
        skill.setUserApplication(this);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
        skill.setUserApplication(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserApplication that = (UserApplication) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
