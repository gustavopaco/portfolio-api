package com.pacoprojects.portfolio.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_section_block")
@Entity
public class ProjectSectionBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_section_block_gen")
    @SequenceGenerator(name = "project_section_block_gen", sequenceName = "project_section_block_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ToString.Exclude
    @ManyToOne(targetEntity = Project.class, optional = false)
    @JoinColumn(name = "project_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "project_section_block_project_fk", value = ConstraintMode.CONSTRAINT)
    )
    private Project project;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProjectSectionBlock that = (ProjectSectionBlock) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
