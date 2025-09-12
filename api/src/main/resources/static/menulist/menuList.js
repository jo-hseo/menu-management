document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest(".menu-name")) {
        const menuId = await e.target.getAttribute("menu-id");
        await moveToMenuDetail(e, menuId);
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#menuDevelopButton")) {
        movePageWithToken(e, "/api/menu/developPage");
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest(".delete-menu")) {
        const menuId = e.target.getAttribute("menu-id");

        var body = JSON.stringify({
            "result": {},
            "body": menuId
        });

        await updateRequest(e, body, "/api/menu/delete");
        await movePageWithToken(e, "/api/menu/listPage");
    }
})

document.getElementById("html_content").addEventListener("click", async(e) => {
    if(e.target.closest(".sale-state")) {
        const menuId = await e.target.getAttribute("menu-id");
        const state = await e.target.innerHTML;

        var body = JSON.stringify({
            "result": {},
            "body": {
                "menu_id": menuId,
                "state": !(state === "판매중")
            }
        });

        await updateRequest(e, body, "/api/menu/changeState");
        await movePageWithToken(e, "/api/menu/listPage");
    }
})