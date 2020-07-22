//Authenticates the user using the authentication api
async function authenticate(redirectPath){
    if(!window.localStorage.getItem("userEmail")){
        let res = await fetch(`/auth?redirectUrl=${redirectPath}`);
        let contentType = res.headers.get("content-type");
        //Stores the users email
        if(contentType === "application/json") {
            let userEmail = await res.json();
            window.localStorage.setItem("userEmail", userEmail.toString());
            window.location.href = redirectPath;
        }
        //Renders a login button
        else {
            let loginHtml = await res.text();
            document.getElementsByTagName("body")[0].innerHTML = loginHtml;
        }
    }
    else {
        if(!window.location.href.includes(redirectPath.substring(1))) {
            window.location.href = redirectPath;
        }
    }
}