console.log("script loaded");



let currentTheme = getTheme() ;

console.log(currentTheme);


changeTheme() ;


//todo
 function changeTheme() {

    document.querySelector("html").classList.add(currentTheme)

    //set event listner for button click

    const changeThemeButton = document.querySelector("#theme_button") ;

    changeThemeButton.querySelector('span').textContent = 
    currentTheme == "light" ? "dark" : "light" ;

    changeThemeButton.addEventListener("click", (event) => {
        const oldTheme = currentTheme ;
        console.log("theme button clicked");
        
        if(currentTheme === "dark"){

            currentTheme = "light" ;

        }

        else currentTheme = "dark" ;
        
        setTheme(currentTheme) ;
        document.querySelector("html").classList.remove(oldTheme) ;
        document.querySelector("html").classList.add(currentTheme) ;

        changeThemeButton.querySelector('span').textContent = 
        currentTheme == "light" ? "dark" : "light" ;

    })

}

// set theme to local storage
function setTheme(theme) {

    localStorage.setItem("theme", theme) ;
}


// get theme from local storage

function getTheme() {

    let theme = localStorage.getItem("theme") ;
    if(theme) return theme ;
    else return "light"
}

