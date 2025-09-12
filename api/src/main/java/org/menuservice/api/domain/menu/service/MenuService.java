package org.menuservice.api.domain.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.domain.menu.converter.MenuConverter;
import org.menuservice.api.domain.menu.model.MenuSelectionItem;
import org.menuservice.db.menu.MenuEntity;
import org.menuservice.db.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuConverter menuConverter;

    //메뉴 생성(메뉴{, 유저id, 메뉴명, 가격, 기획서, 레시피, true}) : 메뉴id
    public Long create(MenuEntity menu) {
        String title = null;
        LocalDateTime lastUpdated = null;
        String detail = null;
        if (menu.getProposal() != null) {
            title = menu.getProposal().getTitle();
            lastUpdated = menu.getProposal().getLastUpdatedDate();
            detail = menu.getProposal().getProposalDetail();
        }

        return menuRepository.createMenu(
                menu.getName(),
                menu.getOnSale(),
                menu.getPrice(),
                title,
                lastUpdated,
                detail,
                menu.getRecipe(),
                menu.getUserId()
        );
    }

    //메뉴 상세 읽기(메뉴id) : 메뉴{유저id, 메뉴id, 메뉴명, 기획서, 레시피, 가격, 판매상태}
    public MenuEntity findMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElse(null);
    }

    //메뉴 조회(유저id) : 메뉴{메뉴id, 유저id,  메뉴명, 가격, [기획서], [레시피], 판매상태}[]
    public List<MenuEntity> findMenus(Long userId) {
        return menuRepository.findAllByUserIdOrderByOnSaleDesc(userId);
    }

//    public List<MenuEntity> findMenuSelectionList(Long userId) {
//        var menus = menuRepository.findAllByUserIdOrderByOnSaleDesc(userId);
//        return menus;
//    }

    //메뉴 수정(메뉴{유저id, 메뉴id, 메뉴명, 기획서, 레시피, 가격, 판매상태})
    public void update(MenuEntity menu) {
        menuRepository.saveMenu(
                menu.getId(),
                menu.getName(),
                menu.getRecipe(),
                menu.getProposal().getTitle(),
                menu.getProposal().getLastUpdatedDate(),
                menu.getProposal().getProposalDetail(),
                menu.getPrice(),
                /*menu.getOnSale(),*/
                menu.getUserId()
        );
    }

    //메뉴 수정(메뉴id, 판매상태)
    public void updateOnSale(Long menuId, Boolean onSale) {
        log.info("Updating menu service entity: {}  {}", menuId, onSale);
        menuRepository.updateOnSale(menuId, onSale);
    }

    public void delete(Long menuId) {
        menuRepository.deleteById(menuId);
    }

}
