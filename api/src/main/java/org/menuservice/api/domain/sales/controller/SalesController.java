package org.menuservice.api.domain.sales.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.domain.menu.converter.MenuConverter;
import org.menuservice.api.domain.menu.service.MenuService;
import org.menuservice.api.domain.sales.converter.SalesConverter;
import org.menuservice.api.domain.sales.model.SalesRequest;
import org.menuservice.api.domain.sales.service.SalesService;
import org.menuservice.db.menu.MenuEntity;
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
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;
    private final MenuService menuService;
    private final SalesConverter salesConverter;
    private final MenuConverter menuConverter;

    @GetMapping(value = "/analyze")
    public String analyzeSales(Model model) {
        var userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var analyzeResult = salesService.analyze((Long) userId);
        model.addAttribute("analyzeResult", analyzeResult);
        return "analyze";
    }

    @GetMapping(value = "/input")
    public String inputSales(Model model) {
        var userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var menuEntityList = menuService.findMenus((Long) userId);
        var menuSelectionList = menuConverter.toSelectionList(menuEntityList);
        model.addAttribute("menus", menuSelectionList);
        return "sales";
    }

    @ResponseBody
    @PostMapping(value = "/save")
    public void saveSales(@RequestBody List<SalesRequest> salesList) {
        var userId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        for(SalesRequest salesRequest : salesList) {
            Long menuId = salesRequest.getMenuId();

            if (menuId == null) {
                var menu = MenuEntity.builder()
                        .name(salesRequest.getMenuName())
                        .price(salesRequest.getPrice())
                        .onSale(true)
                        .userId((Long) userId)
                        .build();
                menuId = menuService.create(menu);
                salesRequest.setMenuId(menuId);
            }
            var salesEntity = salesConverter.toEntity(salesRequest, (Long) userId);
            salesService.save(salesEntity);
        }
    }
}
