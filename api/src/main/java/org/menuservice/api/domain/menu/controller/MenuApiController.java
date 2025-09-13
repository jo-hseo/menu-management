package org.menuservice.api.domain.menu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.common.api.Api;
import org.menuservice.api.domain.menu.converter.MenuConverter;
import org.menuservice.api.domain.menu.model.MenuChangeStateRequest;
import org.menuservice.api.domain.menu.model.MenuDevelopRequest;
import org.menuservice.api.domain.menu.model.MenuListResponseItem;
import org.menuservice.api.domain.menu.model.MenuUpdateRequest;
import org.menuservice.api.domain.menu.service.MenuService;
import org.menuservice.db.menu.MenuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuApiController {

    private final MenuService menuService;
    private final MenuConverter menuConverter;


    //메뉴 개발 페이지
    @GetMapping("/developPage")
    public String developPage() {
        return "develop";
    }

    //메뉴 개발
    @ResponseBody
    @PostMapping("/develop")
    public void developMenu(
            @RequestBody Api<MenuDevelopRequest> request
    ) throws JsonProcessingException {
        Object userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var menu = menuConverter.toEntity((Long)userId, request.getBody());
        menuService.create(menu);
    }

    //메뉴 상세 페이지
//    @ResponseBody
    @PostMapping("/detail")
    public String menuDetail(
            @RequestBody Api<Long> request,
            Model model
    ) throws JsonProcessingException {

        var menuId = request.getBody();
        var menuEntity = menuService.findMenuById(menuId);
        var menuDetail = menuConverter.toDto(menuEntity);

        model.addAttribute("menu", menuDetail);
        return "menuDetail";
    }

    //메뉴 리스트
    @ResponseBody
    @GetMapping("/list")
    public Api<List<MenuListResponseItem>> menuList(Model model) {
        var userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var menus = menuService.findMenus((Long)userId);
        List<MenuListResponseItem> menuListResponse = menuConverter.toListResponse(menus);
        return Api.OK(menuListResponse);
    }

    //메뉴 리스트 페이지
    @GetMapping("/listPage")
    public String menuListPage(Model model) {
        var userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var menus = menuService.findMenus((Long)userId);
        List<MenuListResponseItem> menuListResponse = menuConverter.toListResponse(menus);
        model.addAttribute("menuListResponse", menuListResponse);
        return "menuList";
    }

    //메뉴 수정
    @ResponseBody
    @PostMapping("/update")
    public void menuUpdate(@Valid @RequestBody Api<MenuUpdateRequest> request) throws JsonProcessingException {
        var userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var menu = menuConverter.toEntity(request.getBody());
        menu.setUserId((Long) userId);
        menuService.update(menu);
    }

    //메뉴 판매중지
    @ResponseBody
    @PostMapping("/changeState")
    public void menuStopSale(@Valid @RequestBody Api<MenuChangeStateRequest> request) {
        var menuId = request.getBody().getMenuId();
        var state = request.getBody().getState();
        menuService.updateOnSale(menuId, state);
    }

    //메뉴 삭제
    @ResponseBody
    @PostMapping("/delete")
    public void menuDelete(@Valid @RequestBody Api<Long> request) throws JsonProcessingException {
        var menuId = request.getBody();
        menuService.delete(menuId);
    }
}
