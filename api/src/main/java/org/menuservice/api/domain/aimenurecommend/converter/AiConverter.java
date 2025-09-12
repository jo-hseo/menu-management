package org.menuservice.api.domain.aimenurecommend.converter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.menuservice.api.domain.aimenurecommend.model.MenuRecommendation;
import org.menuservice.api.domain.recipe.Ingredient;
import org.menuservice.api.domain.recipe.Recipe;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiConverter {

    BeanOutputConverter<MenuRecommendation> outputConverter = new BeanOutputConverter<>(MenuRecommendation.class);

    public String getJsonSchema() {
        return this.outputConverter.getJsonSchema();
    }

    public MenuRecommendation getResponseFormat(String content) {
        return this.outputConverter.convert(content);
    }
}
