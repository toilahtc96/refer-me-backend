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
@Table(name = "user_type")
@EqualsAndHashCode(of = "user_type", callSuper = false)
public class UserType extends CommonEntity {
    @Id
    private long id;
    private String code;
    private String meaning;
}
