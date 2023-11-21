package ru.quipy.projections.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {
    private long createdAt;
    private long updatedAt;
}
