package com.pacoprojects.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "curriculum")
@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curriculum_gen")
    @SequenceGenerator(name = "curriculum_gen", sequenceName = "curriculum_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Url file is required")
    private String url;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @ToString.Exclude
    @OneToOne(targetEntity = UserApplication.class, optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_application_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_curriculum_user_application", value = ConstraintMode.CONSTRAINT))
    private UserApplication userApplication;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Curriculum that = (Curriculum) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
