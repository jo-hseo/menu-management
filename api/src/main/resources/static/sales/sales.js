document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#cancelButton")) {
        movePageWithToken(e, "/api/sales/analyze");
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest("#saveButton")) {
        const salesItem = document.querySelectorAll(".sales-item");

        const body = JSON.stringify(
            Array.from(salesItem).map((sales, index) => {
                let menuId = null;
                let menuName;
                var selection = sales.children[0].children[0];
                if(selection.value === 'custom') {
                    menuName = sales.children[0].children[1].value;
                } else {
                    menuId = selection.value;
                    menuName = selection.options[selection.selectedIndex].textContent;
                }

                if(menuName.trim() === "") {
                    return null;
                }

                const salePeriodStart = sales.children[1].children[0].value;
                const salePeriodEnd = sales.children[2].children[0].value;
                const amount = sales.children[3].children[0].value;
                const price = sales.children[4].children[0].value;
                const cost = sales.children[5].children[0].value;

                return {
                    "menu_id": menuId,
                    "menu_name": menuName,
                    "sale_period": "[" + salePeriodStart + ", " + salePeriodEnd + "]",
                    "volume": amount ? parseInt(amount) : 0,
                    "price": price ? parseFloat(price) || 0.0 : 0.0,
                    "cost": cost ? parseFloat(cost) || 0.0 : 0.0
                };
            })
                .filter(item => item !== null)
        );

        await updateRequest(e, body, "/api/sales/save");
        await movePageWithToken(e, "/api/sales/analyze");
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest("#salesAddButton")) {

        const table = document.querySelector("#salesList tbody");
        const newRow = table.insertRow();

        const nameCell = newRow.insertCell();
        const startCell = newRow.insertCell();
        const endCell = newRow.insertCell();
        const volumeCell = newRow.insertCell();
        const priceCell = newRow.insertCell();
        const costCell = newRow.insertCell();
        const deleteCell = newRow.insertCell();

        newRow.className = "sales-item";

        const selection = document.createElement("select");
        const customOption = document.createElement("option");
        const menuNameInput = document.createElement("input");
        menuNameInput.className = "custom-menu-name sales-item-input";
        customOption.text = "직접 입력";
        customOption.value = "custom";
        selection.className = "menu-name-selection sales-item-input";
        nameCell.className = "selection-box sales-item-input";
        selection.appendChild(customOption);
        nameCell.appendChild(selection);
        nameCell.appendChild(menuNameInput);

        const salePeriodStartInput = document.createElement("input");
        salePeriodStartInput.className = "sale-period sales-item-input";
        salePeriodStartInput.setAttribute('type', 'date');
        startCell.appendChild(salePeriodStartInput);

        const salePeriodEndInput = document.createElement("input");
        salePeriodEndInput.className = "sale-period sales-item-input";
        salePeriodEndInput.setAttribute('type', 'date');
        endCell.appendChild(salePeriodEndInput);

        const menuVolumeInput = document.createElement("input");
        menuVolumeInput.className = "menu-volume sales-item-input";
        volumeCell.appendChild(menuVolumeInput)

        const menuPriceInput = document.createElement("input");
        menuPriceInput.className = "menu-price sales-item-input";
        priceCell.appendChild(menuPriceInput);

        const menuCostInput = document.createElement("input");
        menuCostInput.className = "menu-cost sales-item-input";
        costCell.appendChild(menuCostInput);

        const deleteButton = document.createElement("div");
        deleteButton.id = "sales-delete-button";
        deleteButton.style.color = "#767676";
        deleteButton.style.cursor = "pointer";
        deleteButton.innerText = "X";
        deleteCell.appendChild(deleteButton);

        const accessToken = sessionStorage.getItem('accessToken');
        await fetch("/api/menu/list", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "authorization-token": accessToken
            }
        })
            .then(response => response.json())
            .then(data => {
                const menus = data.body;
                for(const menu of menus) {
                    const option = document.createElement("option");
                    option.value = menu.menu_id;
                    option.text = menu.menu_name;
                    selection.appendChild(option);
                }
            });
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#sales-delete-button")) {
        e.target.parentElement.parentElement.remove();
    }
})

document.getElementById("html_content").addEventListener("change", (e) => {
    if(e.target.closest(".menu-name-selection")) {
        const menuNameInput = e.target.parentElement.querySelector("input.custom-menu-name");
        if (e.target.value === "custom") {
            menuNameInput.style.display = 'block';
        } else {
            menuNameInput.style.display = 'none';
        }
    }
})
