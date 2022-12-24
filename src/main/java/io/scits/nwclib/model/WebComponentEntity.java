package io.scits.nwclib.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WEBCOMPONENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebComponentEntity {

    @Builder
    public WebComponentEntity(String title, String description, String script, String thumbnail) {
        this.title = title;
        this.description = description;
        this.script = script;
        this.thumbnail = thumbnail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "script")
    private String script;

    @Column(name = "thumbnail")
    private String thumbnail;

}
