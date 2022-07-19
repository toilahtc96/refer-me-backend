package com.ezyfox.cvconnect.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
@EqualsAndHashCode(of = "role", callSuper = false)
public class Role extends CommonEntity {
    @Id
    private long id;
    private String name;
    private String code;
    private long status;
}
