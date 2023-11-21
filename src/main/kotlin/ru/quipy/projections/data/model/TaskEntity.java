package ru.quipy.projections.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "task")
public class TaskEntity extends BaseEntity{

    @Id
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userAssigned;

    @ManyToOne(fetch = FetchType.LAZY)
    private StatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;
}
