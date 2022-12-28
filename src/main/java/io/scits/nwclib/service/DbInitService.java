package io.scits.nwclib.service;

import io.scits.nwclib.config.NwclibProperties;
import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.model.WebComponentMapper;
import io.scits.nwclib.repository.WebComponentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
// TODO: replace with a JSON repository populator
public class DbInitService {

    private final NwclibProperties nwclibProperties;
    private final WebComponentRepository webComponentRepository;
    private final WebComponentMapper webComponentMapper;

    @PostConstruct
    private void initDb() {
        List<WebComponentDto> webComponents = getWebComponents();
        webComponents.forEach(this::addWebComponentToDb);
    }

    private void addWebComponentToDb(WebComponentDto webComponentDto) {
        webComponentRepository.save(webComponentMapper.toEntity(webComponentDto));
    }

    private List<WebComponentDto> getWebComponents() {
        try {
            List<String> webComponentScripts = Arrays.stream(new PathMatchingResourcePatternResolver().getResources(nwclibProperties.getWebComponentsLocationPattern()))
                    .map(this::getContent)
                    .filter(Objects::nonNull)
                    .toList();

            if (webComponentScripts.size() == 0) {
                log.warn("No web components found for configured location pattern: {}", nwclibProperties.getWebComponentsLocationPattern());
            }
            return webComponentScripts.stream()
                    .map(webComponent -> WebComponentDto.builder()
                            .name("Button1")
                            .description("Some component in the /js/web-components directory")
                            .script(webComponent)
                            .image("iVBORw0KGgoAAAANSUhEUgAAAGsAAABNCAYAAAChSponAAAABHNCSVQICAgIfAhkiAAAABl0RVh0U29mdHdhcmUAZ25vbWUtc2NyZWVuc2hvdO8Dvz4AAAApdEVYdENyZWF0aW9uIFRpbWUATWkgMjggRGV6IDIwMjIgMTc6Mjc6MDkgQ0VUf1WHlAAABN9JREFUeJztnEtoHVUYgL9zZu5NcpPbpEkfMdq80L6QKhWKRUWQqvhY1I3QrkStoLtiEMSudKGbunKhgiClIAgKbYVqKa1CpUrppqmxD0pj2ppn825ukjtzjou5aZ60abi2/vJ/q7n/zPznzPnmPzMwc8d47z2KCOy97oCyeFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIFSWIMK5gfTeTfeiH8oMJt89s2BcK0sQKksQKksQKksQKksQKksQKksQKksQKksQKksQKksQKksQKksQKksQKksQRZMVfXyefEsr5OJZ8fyeNvLvnV1cjk8vJjmch1xMvqWV6LNLS+vPV+3kW1rxf43NisffXiXf0oo7O7ykvLfDHe1J8p/sL3puraxiMfVn31RhSFOm6E3Me1L8b+P/Hic+2Im/moPQYLdWEzy3GhZxbP7SDeLDXfiucUhbbHMF9uVaTFVq6f3pGCP+oQt/LQcGTGM5wfY6zIo0AO7MEO5oD753ErMsxL5Ui91UiR/OE314DvtIJViDax0i9cnD05LC4tdB0WXFP3ZDOGPkIze9nIuJvryMCQzhrsZkoA50YlaWYDdX3TKv7xpP9q1JE+6sxw9OEh/oxHePE+5+EOzSzuR4/xUIDOGbjRB54m+u4g52ErzegG8fI97XgVlbQbhjDe7nXuL9VzDvZzCliQx3YRS7PkvwSl2SUFJluV+vzw8WBtKdH4HRCPPMSkxDBtOQIT7ei/u9/7ay3G/9EHvs86sxG7MYwF8cxbUO46/kMA2ZJfXX5x2MeXxHDtNcTvjBOggK/T09AEDw9ArMfaXYbatwpwfxpwYwT9UkCQJDsOOB6ZOlIMmkBFRW6qONUBbc/J3f0waTheoaigBwv/ThThSk5t3iqqI/D4BZPmPKq0yW/WAe0zB7cxOY5DISz/lyRJT8NoU2w9caiL+7RnyoM1lfERK8WIvdshw/lLQZfd0xa5r2/ZPT7dSkZ/d/SlIooLJuSWFw7dZq7JMrpuOLOQmrC2IG8pj6QmwhgVMUrjm+fQzTXJ7EIo9vL9wd1iTrTUOGcPdDkItx7Tdwh7qIv7+G3VyFqUzhgeDV+zFrpivXlMzo8Bwntqkc83Yzpq5sEQd1Z9xVWXZdBXEmwLeNwIYs/kaMO96L3VSJeXbVrfd9vBp3sh93pBuTtvi+Cdy5EUx9ZtZA3tx+SzXuxHXin7rxfROwLIVvG8b3T2I2ZDGrS2AkIr/3IrY+g922ElMeQqlNqsOC3VyVtHlqgKAqhbswiv9jmOCFWkzjwtOuOzeCOz2YTJ1rK4oxbDe5u5VVFhDuaiI+1Em0rwOswW7MYp+oue2upraU8K0m4sNdRPs7MKUB9rEqgpdqF7yTNKtKCN9pJj7Sg/tzBCYcpjqdTHFT15tsSLC9Dnesh+jzy8ndYF0Z4RuNYA2mqZxg5xrcsV6iLy5jKkLslmrM+iyMx/MbBXzfBP78CP7RysXc4N4RZu7ngPQlz3uPvuT5P0BlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCUJlCWLew0flv4tWliBUliBUliBUliBUliBUliBUliBUliBUliBUliBUliBUliBUliBUliBUliBUliD+Ad5kf4r4vyMUAAAAAElFTkSuQmCC")
                            .build())
                    .collect(Collectors.toList());
        } catch (IOException e){
            log.error("Failed to get web component scripts from location pattern: {}", nwclibProperties.getWebComponentsLocationPattern());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String getContent(Resource resource) {
        try {
            return new String(resource.getInputStream()
                    .readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to read file! resource: {}", resource);
            e.printStackTrace();
            return null;
        }
    }
}
