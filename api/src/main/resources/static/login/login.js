var loginButton = document.getElementById("loginButton");
if(loginButton) {
    loginButton.addEventListener('click', async function (e) {
        const id = await document.getElementById("loginId").value;
        const password = await document.getElementById("password").value;

        await accessRequest(e, "/open-api/user/login", id, password);
    });
}