package ru.quipy.projections.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usr")
public class UserEntity extends BaseEntity {

    @Id
    private String username;

    private String fullName;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<ProjectEntity> projectsCreated;
}
