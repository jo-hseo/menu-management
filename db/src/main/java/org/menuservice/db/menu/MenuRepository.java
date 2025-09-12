package org.menuservice.db.menu;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    //메뉴 조회(유저id) : 메뉴{메뉴id, 유저id,  메뉴명, 가격, [기획서], [레시피], 판매상태}[]
    public List<MenuEntity> findAllByUserIdOrderByOnSaleDesc(Long userId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE menu SET " +
            "name = :name, " +
            "/*on_sale = :onSale,*/ " +
            "price = :price, " +
            "proposal = row(:proposalName, :lastUpdatedDate, cast(:proposalDetail as json)), " +
            "recipe = cast(:recipe as json), " +
            "user_id = :userId " +
            "WHERE id = :id "
            , nativeQuery = true)
    void saveMenu(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("recipe") String recipe,
            @Param("proposalName") String proposalName,
            @Param("lastUpdatedDate") LocalDateTime lastUpdatedDate,
            @Param("proposalDetail") String proposalDetail,
            @Param("price") BigDecimal price,
//            @Param("onSale") Boolean onSale,
            @Param("userId") Long userId
    );

    @Query(value = "SELECT create_menu_with_return(?1, ?2, ?3, ?4, ?5, cast(?6 as json), cast(?7 as json), ?8)", nativeQuery = true)
    Long createMenu(
            String name,
            Boolean onSale,
            BigDecimal price,
            String title,
            LocalDateTime lastUpdatedDate,
            String proposalDetail,
            String recipe,
            Long userId
    );

    //메뉴 수정(메뉴id, 판매상태)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE menu SET on_sale = :onSale WHERE id = :menuId", nativeQuery = true)
    void updateOnSale(@Param("menuId") Long menuId, @Param("onSale") Boolean onSale);
}
