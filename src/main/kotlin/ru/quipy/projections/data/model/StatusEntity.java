package ru.quipy.projections.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "status")
public class StatusEntity extends BaseEntity {

    @Id
    private UUID id;

    private String name;
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;

}
