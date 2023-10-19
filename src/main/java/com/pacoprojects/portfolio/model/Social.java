package com.pacoprojects.portfolio.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "social")
@Entity
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "social_gen")
    @SequenceGenerator(name = "social_gen", sequenceName = "social_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "github")
    private String github;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "youtube")
    private String youtube;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_application_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_social_user_application", value = ConstraintMode.CONSTRAINT))
    private UserApplication userApplication;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Social social = (Social) o;
        return getId() != null && Objects.equals(getId(), social.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
