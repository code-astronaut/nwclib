package io.scits.nwclib.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "WEBCOMPONENT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "script")
    private String script;

    @OneToMany(mappedBy = "webComponentImageEntity")
    private List<WebComponentImageEntity> webComponentImageEntityList;

    public void addWebComponentImages(List<WebComponentImageEntity> webComponentImageEntityList) {
        this.getWebComponentImageEntityList().addAll(webComponentImageEntityList);
    }

    public void removeWebComponentImage(WebComponentImageEntity webComponentImageEntity) {
        this.getWebComponentImageEntityList().remove(webComponentImageEntity);
    }

}
