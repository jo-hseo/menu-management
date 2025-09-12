// document.getElementById("html_content").addEventListener("click", (e) => {
//     if(e.target.closest("#menuEditCancelButton")) {
//         // history.back();
//         movePageWithToken(e, "/api/menu/listPage");
//     }
// })

// document.getElementById("html_content").addEventListener("click", async (e) => {
//     if(e.target.closest("#menuEditSaveButton")) {
//
//         const menuId = document.getElementById("menuName").getAttribute("menu-id");
//         const menuName = document.getElementById("menuName").value;
//         const menuPrice = document.getElementById("menuPrice").value;
//         const proposalName = document.getElementById("proposalName").value;
//         const proposalGoal = document.getElementById("proposalGoal").value;
//         const proposalTarget = document.getElementById("proposalTarget").value;
//         const proposalWhen = document.getElementById("proposalWhen").value;
//         const proposalWhere = document.getElementById("proposalWhere").value;
//         const proposalPrice = document.getElementById("proposalPrice").value;
//         const proposalVolume = document.getElementById("proposalVolume").value;
//         const ingredientNames = document.querySelectorAll("input.ingredient-name");
//         const ingredientAmountArray = document.querySelectorAll("input.ingredient-amount");
//         const ingredientAmountUnitArray = document.querySelectorAll("input.ingredient-amount-unit");
//         const ingredientPriceArray = document.querySelectorAll("input.ingredient-price");
//         const ingredients = Array.from(ingredientNames).map((nameInput, index) => {
//             const amountInput = ingredientAmountArray[index];
//             const priceInput = ingredientPriceArray[index];
//
//             if(nameInput.value.trim() !== "") {
//                 return {
//                     name: nameInput.value,
//                     amount: amountInput ? parseInt(amountInput.value) : 0,
//                     amountUnit: ingredientAmountUnitArray.value,
//                     price: priceInput ? parseFloat(priceInput.value) || 0 : 0
//                 };
//             } else {
//                 return null;
//             }
//         })
//             .filter(item => item !== null);
//         const recipeProcedure = document.getElementById("recipeProcedure").value;
//         const recipeDesign = document.getElementById("recipeDesign").value;
//         const recipeDesignDescription = document.getElementById("recipeDesignDescription").value;
//         const recipeTotalCost = document.getElementById("ingredientTotalCost").textContent;
//
//         const body = JSON.stringify({
//             "result": {
//                 "result_code" : "200",
//                 "result_message" : "성공",
//                 "result_description" : ""
//             },
//             "body" : {
//                 "menu_id" : parseInt(menuId),
//                 "menu_name" : menuName ? menuName : "",
//                 "price" : menuPrice ? parseFloat(menuPrice) : 0.0,
//                 "proposal" : {
//                     "title": proposalName ? proposalName : "",
//                     "last_updated_date": new Date(Date.now()).toISOString(),
//                     "proposal_detail": {
//                         "name": proposalName ? proposalName : "",
//                         "goal": proposalGoal ? proposalGoal : "",
//                         "target": proposalTarget ? proposalTarget : "",
//                         "when": proposalWhen ? proposalWhen : "",
//                         "where": proposalWhere ? proposalWhere : "",
//                         "price": proposalPrice ? proposalPrice : "",
//                         "volume": proposalVolume ? proposalVolume : ""
//                     }
//                 },
//                 "recipe" : {
//                     "ingredients" : ingredients ? ingredients : "",
//                     "procedure" : recipeProcedure ? recipeProcedure : "",
//                     "design" : recipeDesign ? recipeDesign : "",
//                     "design_description" : recipeDesignDescription ? recipeDesignDescription : "",
//                     "cost" : recipeTotalCost ? parseFloat(recipeTotalCost) : 0.0
//                 }
//             }
//         })
//
//         await updateRequest(e, body, "/api/menu/update");
//         await movePageWithToken(e, "/api/menu/listPage");
//     }
// })