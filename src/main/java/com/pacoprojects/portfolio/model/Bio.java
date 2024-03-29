package com.pacoprojects.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "bio")
@Entity
public class Bio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bio_gen")
    @SequenceGenerator(name = "bio_gen", sequenceName = "bio_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "avatar_url", columnDefinition = "TEXT")
    private String avatarUrl;

    @Column(name = "full_name", length = 50)
    @Size(max = 50, message = "Full name must be less than 50 characters")
    private String fullName;

    @Column(name = "job_title", length = 50)
    @Size(max = 50, message = "Job title must be less than 50 characters")
    private String jobTitle;

    @Column(name = "presentation", columnDefinition = "TEXT", length = 250)
    @Size(max = 250, message = "Presentation must be less than 250 characters")
    private String presentation;

    @Column(name = "testimonial", columnDefinition = "TEXT", length = 1000)
    @Size(max = 1000, message = "Testimonial must be less than 1000 characters")
    private String testimonial;

    @ToString.Exclude
    /* The attribute orphanRemoval = false, because the user can delete the bio but the userApplication still exists */
    @OneToOne(targetEntity = UserApplication.class, optional = false, orphanRemoval = false)
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
        Bio bio = (Bio) o;
        return getId() != null && Objects.equals(getId(), bio.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
