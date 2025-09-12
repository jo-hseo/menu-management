package org.menuservice.api.domain.menu.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.domain.menu.model.*;
import org.menuservice.api.domain.proposal.Proposal;
import org.menuservice.api.domain.proposal.ProposalDetail;
import org.menuservice.api.domain.recipe.Recipe;
import org.menuservice.db.menu.MenuEntity;
import org.menuservice.db.proposal.ProposalEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuConverter {
    ObjectMapper objectMapper = new ObjectMapper();

    public MenuEntity toEntity(@Valid MenuUpdateRequest request) throws JsonProcessingException {

        var proposal = request.getProposal();
        ProposalEntity proposalEntity = ProposalEntity.builder()
                .title(proposal.getTitle())
                .lastUpdatedDate(proposal.getLastUpdatedDate())
                .proposalDetail(objectMapper.writeValueAsString(proposal.getProposalDetail()))
                .build();

        String recipe = objectMapper.writeValueAsString(request.getRecipe());

        var entity = MenuEntity.builder()
                .id(request.getMenuId())
                .name(request.getMenuName())
                .price(request.getPrice())
                .proposal(proposalEntity)
                .recipe(recipe)
                .build();

        return entity;
    }

    public MenuEntity toEntity(Long userId, @Valid MenuDevelopRequest request) throws JsonProcessingException {

        var proposal = request.getProposal();
        ProposalEntity proposalEntity = ProposalEntity.builder()
                .title(proposal.getTitle())
                .lastUpdatedDate(proposal.getLastUpdatedDate())
                .proposalDetail(objectMapper.writeValueAsString(proposal.getProposalDetail()))
                .build();

        String recipe = objectMapper.writeValueAsString(request.getRecipe());

        return MenuEntity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .proposal(proposalEntity)
                .recipe(recipe)
                .onSale(false)
                .userId(userId)
                .build();
    }

    public List<MenuListResponseItem> toListResponse(List<MenuEntity> entityList) {
        var result = new ArrayList<MenuListResponseItem>();
        for (MenuEntity entity : entityList) {
            String formattedDate = null;
            if(entity.getProposal() != null) {
                LocalDateTime proposalDate = entity.getProposal().getLastUpdatedDate();
                formattedDate = proposalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            var menu = MenuListResponseItem.builder()
                    .menuId(entity.getId())
                    .menuName(entity.getName())
                    .proposalDate(formattedDate)
                    .state(entity.getOnSale())
                    .build();
            result.add(menu);
        }

        return result;
    }

    public MenuDto toDto(MenuEntity menuEntity) throws JsonProcessingException {

        var proposalEntity = menuEntity.getProposal();
        Proposal proposal = null;
        if (proposalEntity != null) {
            proposal = Proposal.builder()
                    .title(proposalEntity.getTitle())
                    .lastUpdatedDate(proposalEntity.getLastUpdatedDate())
                    .proposalDetail(objectMapper.readValue(proposalEntity.getProposalDetail(), ProposalDetail.class))
                    .build();
        }


        var recipeEntity = menuEntity.getRecipe();
        Recipe recipe = null;
        if (recipeEntity != null) {
            recipe = objectMapper.readValue(recipeEntity, Recipe.class);
        }


        var menuDto = MenuDto.builder()
                .id(menuEntity.getId())
                .name(menuEntity.getName())
                .price(menuEntity.getPrice())
                .proposal(proposal)
                .recipe(recipe)
                .onSale(menuEntity.getOnSale())
                .userId(menuEntity.getUserId())
                .build();

        return menuDto;
    }

    public List<MenuSelectionItem> toSelectionList(List<MenuEntity> menus) {
        var result = new ArrayList<MenuSelectionItem>();
        for(var menu : menus) {
            var item = MenuSelectionItem.builder()
                    .menuId(menu.getId())
                    .menuName(menu.getName())
                    .build();
            result.add(item);
        }

        return result;
    }
}
