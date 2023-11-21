package ru.quipy.projections.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "project")
public class ProjectEntity extends BaseEntity{

    @Id
    private UUID id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<TaskEntity> tasks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<StatusEntity> statuses;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserEntity> members;

}
