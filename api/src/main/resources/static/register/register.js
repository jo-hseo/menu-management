var registerButton = document.getElementById("registerButton");
if(registerButton) {
    registerButton.addEventListener("click", async function (e) {
        const id = await document.getElementById("registerId").value;
        const password = await document.getElementById("registerPassword").value;

        await accessRequest(e, "/open-api/user/register", id, password);
    });
}