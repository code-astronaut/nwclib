package io.scits.nwclib.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "webcomponent")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "script")
    private String script;

    @Column(name = "image")
    private String image;

}
