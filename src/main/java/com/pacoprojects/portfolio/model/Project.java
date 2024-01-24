package com.pacoprojects.portfolio.model;

import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import com.pacoprojects.portfolio.model.enums.converter.ProjectStatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "project")
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_gen")
    @SequenceGenerator(name = "project_gen", sequenceName = "project_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Column(name = "picture_url", columnDefinition = "TEXT")
    private String pictureUrl;

    @Column(name = "picture_orientation", length = 20)
    private String pictureOrientation;

    @Column(name = "status", length = 20, nullable = false)
    @Convert(converter = ProjectStatusConverter.class)
    @NotNull(message = "Project status is mandatory")
    private ProjectStatus status;

    @Column(name = "tag")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_tags",
            joinColumns = @JoinColumn(name = "project_id"))
    private Set<String> tags = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProjectSectionBlock> projectSectionBlocks = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_application_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_application_id_fk", value = ConstraintMode.CONSTRAINT))
    private UserApplication userApplication;

    public void addProjectSectionBlock(ProjectSectionBlock projectSectionBlock) {
        projectSectionBlocks.add(projectSectionBlock);
        projectSectionBlock.setProject(this);
    }

    public void removeProjectSectionBlock(ProjectSectionBlock projectSectionBlock) {
        projectSectionBlocks.remove(projectSectionBlock);
        projectSectionBlock.setProject(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Project project = (Project) o;
        return getId() != null && Objects.equals(getId(), project.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
