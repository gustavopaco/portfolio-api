package com.pacoprojects.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "skill")
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_gen")
    @SequenceGenerator(name = "skill_gen", sequenceName = "skill_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "Rating is mandatory")
    private Integer rating;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Url is mandatory")
    private String url;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_application_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_application_id_fk", value = ConstraintMode.CONSTRAINT))
    @NotNull(message = "UserApplication is mandatory")
    private UserApplication userApplication;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Skill skill = (Skill) o;
        return getId() != null && Objects.equals(getId(), skill.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
