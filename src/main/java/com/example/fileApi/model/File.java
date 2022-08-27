package com.example.fileApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILES")
public class File implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private Long size;
    @NotNull
    private String path;
    @NotNull
    private String extension;

}
