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
@Table(name = "resume")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_gen")
    @SequenceGenerator(name = "resume_gen", sequenceName = "resume_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Url file is required")
    private String url;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @ToString.Exclude
    /* The attribute orphanRemoval = false, because the user can delete the resume but the userApplication still exists */
    @OneToOne(targetEntity = UserApplication.class, optional = false, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_application_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_resume_user_application", value = ConstraintMode.CONSTRAINT))
    private UserApplication userApplication;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Resume that = (Resume) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
