console.log("contact.js");
const baseurl = "http://localhost:8081"

const viewContactModal = document.getElementById('view_contact_modal') ;

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


const contactModal = new Modal(viewContactModal, options, instanceOptions) ;

function openContactModal() {

    return contactModal.show() ;
}

function closeContactModal() {

    contactModal.hide() ;
}


async function loadContactData(id) {
    console.log(id);
try {
const data = await (
    await fetch(`${baseurl}/api/contact/${id}`)
    ).json();
console.log(data);

document.querySelector("#contact_name").innerHTML = data.name ;
document.querySelector("#contact_email").innerHTML = data.email ;
document.querySelector("#contact_image").src= data.contactImage;
document.querySelector("#contact_address").innerHTML = data.address;
document.querySelector("#contact_phoneno").innerHTML = data.phoneno;
document.querySelector("#contact_about").innerHTML = data.description;
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


//delete contact

async function deleteContact(id) {

    Swal.fire({
        title: "Do you want to delete the contact?",
        icon:"Warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
       
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
        //   Swal.fire("Deleted!", "", "success");

        const url = `${baseurl}/user/contact/delete/` + id ;
        window.location.replace(url) ;
            
        } 
      });
}
