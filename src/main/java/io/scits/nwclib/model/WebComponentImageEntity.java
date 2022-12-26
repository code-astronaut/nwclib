package io.scits.nwclib.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WEBCOMPONENT_IMAGE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebComponentImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "web_component_entity_id")
    private WebComponentEntity webComponentEntity;

}
