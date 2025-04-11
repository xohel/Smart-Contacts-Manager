console.log("user.js");
const baseurl = "http://localhost:8081"

const viewUserModal = document.getElementById('view_user_modal') ;

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};


const userModal = new Modal(viewUserModal, options, instanceOptions) ;

function openUserModal() {

    return userModal.show() ;
}

function closeContactModal() {

    userModal.hide() ;
}


async function loadUserData(eMail) {
    console.log(eMail);
try {
const data = await (
    await fetch(`${baseurl}/api/contact/${eMail}`)
    ).json();
console.log(data);

document.querySelector("#user_name").innerHTML = data.name ;
document.querySelector("#user_email").innerHTML = data.email ;
document.querySelector("#user_image").src= data.contactImage;
document.querySelector("#user_address").innerHTML = data.address;
document.querySelector("#user_phoneno").innerHTML = data.phoneno;
document.querySelector("#user_about").innerHTML = data.description;
const contactFavouraite = document.querySelector("#contact_favourite");
if (data.favourite) {
    contactFavouraite.innerHTML = "Favourite contact"
}
else {
    contactFavouraite.innerHTML = "Not Favourite Contact" 
}

document.querySelector("#contact_website").href = data.website_link;
document.querySelector("#contact_website").innerHTML = data.website_link;
document.querySelector("#contact_linkedin").href = data.linkedin;
document.querySelector("#contact_linkedin").innerHTML = data.linkedin;

openContactModal();

openContactModal() ;
} catch (error) {
    console.log("Error: " , error);
    
}

}


