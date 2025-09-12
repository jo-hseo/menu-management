document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#menuIcon")) {
        movePageWithToken(e, "/api/sales/analyze");
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#salesInputMenu")) {
        movePageWithToken(e, "/api/sales/input");
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#menuMenageMenu")) {
        movePageWithToken(e, "/api/menu/listPage");
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#loginMenu")) {
        window.location.href = "/open-api/user/loginPage";
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#signOnMenu")) {
        window.location.href = "/open-api/user/registerPage";
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest("#logoutMenu")) {
        await sessionStorage.removeItem("accessToken");
        window.location.href = "/open-api/user/loginPage";
    }
})




document.getElementById("html_content").addEventListener("input", (e) => {
    if(e.target.closest("input.proposal-input-text")) {
        const proposalInputs = document.querySelectorAll("input.proposal-input-text");
        const menuRecommendButton = document.getElementById("menuRecommendButton");
        const allFilled = Array.from(proposalInputs).every(input => input.value.trim() !== "");

        if(allFilled) {
            menuRecommendButton.disabled = false;
            menuRecommendButton.style.cursor = 'pointer';
            menuRecommendButton.style.color = 'white';
            menuRecommendButton.style.backgroundColor = 'black';
        } else {
            menuRecommendButton.disabled = true;
            menuRecommendButton.style.cursor = 'auto';
            menuRecommendButton.style.color = '#DDDDDDFF';
            menuRecommendButton.style.backgroundColor = '#f3f3f3';
        }
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#menuRecommendButton")) {

        const accessToken = sessionStorage.getItem('accessToken');

        const proposalName = document.getElementById("proposalName").value;
        const proposalGoal = document.getElementById("proposalGoal").value;
        const proposalTarget = document.getElementById("proposalTarget").value;
        const proposalWhen = document.getElementById("proposalWhen").value;
        const proposalWhere = document.getElementById("proposalWhere").value;
        const proposalPrice = document.getElementById("proposalPrice").value;
        const proposalVolume = document.getElementById("proposalVolume").value;

        var body =
            "기획명 : " + proposalName +
            "\n목표 : " + proposalGoal +
            "\n타겟 고객 : " + proposalTarget +
            "\n판매 시기 : " + proposalWhen +
            "\n매장 특징 : " + proposalWhere +
            "\n원하는 판매가 : " + proposalPrice +
            "\n원하는 판매량 : " + proposalVolume;
        var httpBody = JSON.stringify({
            "result": {

            },
            "body": body
        })

        fetch("/api/ai-menu-recommend/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "authorization-token": accessToken
            },
            body: httpBody
        })
            .then(response => response.json())
            .then(async data => {
                const recommend = data.body;
                document.getElementById("recipeProcedure").value = recommend.recipe.procedure;
                document.getElementById("recipeDesignDescription").value = recommend.recipe.design_description;
                document.getElementById("menuPrice").value = recommend.price;

                const items = document.querySelectorAll(".list-item");
                Array.from(items).map(item => {
                    item.remove();
                });

                Array.from(recommend.recipe.ingredients).map(ingredient => {
                    const listItem = createIngredientElement();
                    listItem.children[0].children[0].value = ingredient.name;
                    listItem.children[1].children[0].value = ingredient.amount;
                    listItem.children[2].children[0].value = ingredient.amount_unit;
                    listItem.children[3].children[0].value = ingredient.price;
                });

                sumIngredientPrice();
            })
    }
})

document.getElementById("html_content").addEventListener("input", (e) => {
    if(e.target.closest("input.ingredient-price")) {
        sumIngredientPrice();
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#ingredientAddButton")) {
        createIngredientElement();
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#ingredient-delete-button")) {
        e.target.parentElement.parentElement.remove();
    }
})

document.getElementById("html_content").addEventListener("click", (e) => {
    if(e.target.closest("#menuEditCancelButton") || e.target.closest("#developCancelButton")) {
        movePageWithToken(e, "/api/menu/listPage");
    }
})

document.getElementById("html_content").addEventListener("click", async (e) => {
    if(e.target.closest("#menuEditSaveButton") || e.target.closest("#developSaveButton")) {

        const menuName = document.getElementById("menuName").value;
        const menuPrice = document.getElementById("menuPrice").value;
        const proposalName = document.getElementById("proposalName").value;
        const proposalGoal = document.getElementById("proposalGoal").value;
        const proposalTarget = document.getElementById("proposalTarget").value;
        const proposalWhen = document.getElementById("proposalWhen").value;
        const proposalWhere = document.getElementById("proposalWhere").value;
        const proposalPrice = document.getElementById("proposalPrice").value;
        const proposalVolume = document.getElementById("proposalVolume").value;
        const ingredientNames = document.querySelectorAll("input.ingredient-name");
        const ingredientAmountArray = document.querySelectorAll("input.ingredient-amount");
        const ingredientAmountUnitArray = document.querySelectorAll("input.ingredient-amount-unit");
        const ingredientPriceArray = document.querySelectorAll("input.ingredient-price");
        const ingredients = Array.from(ingredientNames).map((nameInput, index) => {
            const amountInput = ingredientAmountArray[index];
            const priceInput = ingredientPriceArray[index];

            if(nameInput.value.trim() !== "") {
                return {
                    name: nameInput.value,
                    amount: amountInput ? parseInt(amountInput.value) : 0,
                    amountUnit: ingredientAmountUnitArray.value,
                    price: priceInput ? parseFloat(priceInput.value) || 0 : 0
                };
            } else {
                return null;
            }
        })
            .filter(item => item !== null);
        const recipeProcedure = document.getElementById("recipeProcedure").value;
        const recipeDesign = document.getElementById("recipeDesign").value;
        const recipeDesignDescription = document.getElementById("recipeDesignDescription").value;
        const recipeTotalCost = document.getElementById("ingredientTotalCost").textContent;

        const body = {
            "result": {
                "result_code" : "200",
                "result_message" : "성공",
                "result_description" : ""
            },
            "body" : {
                // "menu_id" : parseInt(menuId),
                "menu_name" : menuName ? menuName : "",
                "price" : menuPrice ? parseFloat(menuPrice) : 0.0,
                "proposal" : {
                    "title": proposalName ? proposalName : "",
                    "last_updated_date": new Date(Date.now()).toISOString(),
                    "proposal_detail": {
                        "name": proposalName ? proposalName : "",
                        "goal": proposalGoal ? proposalGoal : "",
                        "target": proposalTarget ? proposalTarget : "",
                        "when": proposalWhen ? proposalWhen : "",
                        "where": proposalWhere ? proposalWhere : "",
                        "price": proposalPrice ? proposalPrice : "",
                        "volume": proposalVolume ? proposalVolume : ""
                    }
                },
                "recipe" : {
                    "ingredients" : ingredients ? ingredients : "",
                    "procedure" : recipeProcedure ? recipeProcedure : "",
                    "design" : recipeDesign ? recipeDesign : "",
                    "design_description" : recipeDesignDescription ? recipeDesignDescription : "",
                    "cost" : recipeTotalCost ? parseFloat(recipeTotalCost) : 0.0
                }
            }
        }

        if(e.target.closest("#menuEditSaveButton")) {
            const menuId = document.getElementById("menuName").getAttribute("menu-id");
            body.body.menu_id = parseInt(menuId);
            await updateRequest(e, JSON.stringify(body), "/api/menu/update");
        } else {
            await updateRequest(e, JSON.stringify(body), "/api/menu/develop");
        }
        await movePageWithToken(e, "/api/menu/listPage");
    }
})









function createIngredientElement() {
    const table = document.querySelector("#ingredientTable tbody");
    const newRow = table.insertRow();

    const nameCell = newRow.insertCell(0);
    const amountCell = newRow.insertCell(1);
    const amountUnitCell = newRow.insertCell(2);
    const priceCell = newRow.insertCell(3);
    const deleteCell = newRow.insertCell(4);

    // 각 셀 내용 입력
    nameCell.innerHTML = `<input class="ingredient-name name">`;
    amountCell.innerHTML = `<input class="ingredient-amount name">`;
    amountUnitCell.innerHTML = `<input class="ingredient-amount-unit name">`
    priceCell.innerHTML = `<input class="ingredient-price name">`;
    deleteCell.innerHTML = `<div id="ingredient-delete-button" style="color: #767676; cursor: pointer;">X</div>`;

    return newRow;
}

async function updateRequest(e, httpBody, url) {
    const accessToken = await sessionStorage.getItem('accessToken');
    await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "authorization-token": accessToken
        },
        body: httpBody
    })
        .catch(reason => console.log(reason));
}

async function moveToMenuDetail(e, menuId) {
    const accessToken =  await sessionStorage.getItem('accessToken');
    await fetch("/api/menu/detail", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "authorization-token": accessToken
        },
        body: JSON.stringify({
            "result": {

            },
            "body": menuId
        })
    })
        .then(response => response.text())
        .then((data)=> {
            document.getElementById("html_content").innerHTML = data;
        })
        .catch(reason => console.log(reason));
}

async function accessRequest(e, url, id, pw) {
    await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "result": {

            },
            "body": {
                "user_id": id,
                "password": pw
            }
        })
    })
        .then((response) => response.json())
        .then((data) => {
            sessionStorage.setItem('accessToken', data.body.access_token);
            movePageWithToken(e, "/api/sales/analyze");
        })
        .catch(reason => {
            console.log(reason);
        });
}

async function movePageWithToken(event, url) {
    const accessToken = await sessionStorage.getItem('accessToken');

    await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "authorization-token": accessToken
        }
    })
        .then(response => response.text())
        .then((data)=> {
            document.getElementById("html_content").innerHTML = data;
        })
        .catch(reason => console.log(reason));
}

function loadScript(src) {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = src;
        script.defer = true;

        script.onload = () => resolve(script);
        script.onerror = () => reject(new Error(`스크립트 로드 실패: ${src}`));

        document.head.appendChild(script);
    });
}

async function loadAllScripts(script) {
    if(document.readyState === "loading") {
        document.addEventListener('DOMContentLoaded', async (e) => {
            console.log("스크립트 로드");
            try {
                await loadScript(script);
                // await loadScript('/common.js');
            } catch (error) {
                console.error(error);
            }
        });
    } else {
        try {
            await loadScript(script);
            // await loadScript('/common.js');
        } catch (error) {
            console.error(error);
        }
    }
}

function sumIngredientPrice() {
    const ingredientPriceInputs = document.querySelectorAll("input.ingredient-price");
    const ingredientTotalCost = document.getElementById("ingredientTotalCost");
    let total = 0;
    ingredientPriceInputs.forEach(input => {
        const value = parseFloat(input.value) || 0;
        total += value;
    });
    ingredientTotalCost.textContent = total;
}