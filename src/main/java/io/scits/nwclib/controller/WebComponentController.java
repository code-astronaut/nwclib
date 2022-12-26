package io.scits.nwclib.controller;

import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.service.WebComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("webcomponents")
@RequiredArgsConstructor
public class WebComponentController {
    private final WebComponentService webComponentService;

    @PostMapping
    public ResponseEntity<WebComponentDto> createWebComponent(@RequestBody WebComponentDto createData,
                                                              @RequestParam("multipartFile") MultipartFile multipartFile) {
        WebComponentDto webComponent = webComponentService.createWebComponent(createData, multipartFile);
        return new ResponseEntity<>(webComponent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebComponentDto> getWebComponent(@PathVariable("id") Long id) {
        WebComponentDto webComponent = webComponentService.getWebComponent(id);
        return ResponseEntity.ok(webComponent);
    }

    @GetMapping
    public ResponseEntity<List<WebComponentDto>> getWebComponents(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(defaultValue = "0") Integer pageSize) {
        List<WebComponentDto> webComponents = webComponentService.getWebComponents(pageNumber, pageSize);
        return ResponseEntity.ok(webComponents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebComponentDto> updateWebComponent(@PathVariable("id") Long id,
                                                              @RequestBody WebComponentDto updateData) {
        WebComponentDto webComponent = webComponentService.updateWebComponent(id, updateData);
        return ResponseEntity.ok(webComponent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WebComponentDto> patchWebComponent(@PathVariable("id") Long id,
                                                             @RequestBody WebComponentDto patchData) {
        WebComponentDto webComponent = webComponentService.patchWebComponent(id, patchData);
        return ResponseEntity.ok(webComponent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebComponent(@PathVariable("id") Long id) {
        webComponentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
