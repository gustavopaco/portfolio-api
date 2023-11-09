package com.pacoprojects.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_gen")
    @SequenceGenerator(name = "course_gen", sequenceName = "course_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    @Size(max = 64, message = "Name must be less than 64 characters")
    private String name;

    @Column(name = "issuer", nullable = false, length = 64)
    @Size(max = 64, message = "Issuer must be less than 64 characters")
    private String issuer;

    @Column(name = "end_date", nullable = false)
    @Past(message = "End date must be in the past")
    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_application_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_application_id_fk", value = ConstraintMode.CONSTRAINT))
    private UserApplication userApplication;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Course course = (Course) o;
        return getId() != null && Objects.equals(getId(), course.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
