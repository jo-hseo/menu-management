var trailingButton = document.getElementById("trailingButton");
if(trailingButton) {
    trailingButton.addEventListener("click", function (e) {
        //정렬 선택1
    });
}

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest(".menu-name")) {
        const menuId = e.target.getAttribute("menu-id");
        moveToMenuDetail(e, menuId);
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest("#recommendedActionEditButton")) {
        const menuId = await e.target.getAttribute("menu-id");
        console.log(menuId);
        await moveToMenuDetail(e, menuId);
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest("#recommendedActionStopButton")) {
        const menuId = await e.target.getAttribute("menu-id");
        const accessToken = await sessionStorage.getItem('accessToken');
        await fetch("/api/menu/changeState", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "authorization-token": accessToken
            },
            body: JSON.stringify({
                "result": {

                },
                "body": {
                    "menu_id": menuId,
                    "state": false
                }
            })
        })
            .then(response => {
                movePageWithToken(e, "/api/sales/analyze");
                // location.reload();
            })
            .catch(reason => console.log(reason));

        // await loadAllScripts("menudetail/menuDetail.js");
    }
})

