
//Authenticates the user using the authentication api
async function authenticate(redirectPath){
    let res = await fetch(`/auth?redirectUrl=${redirectPath}`);
    let contentType = res.headers.get("content-type");
    //Stores the users email
    if(contentType === "application/json") {
        let userEmail = await res.json();
        window.userEmail = userEmail.toString();
        window.location.href = redirectPath;
    }
    //Renders a login button
    else {
        let loginHtml = await res.text();
        document.getElementsByTagName("body")[0].innerHTML = loginHtml;
    }
    
}