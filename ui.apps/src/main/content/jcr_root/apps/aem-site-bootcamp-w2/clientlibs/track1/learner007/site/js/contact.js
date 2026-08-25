const contactFormData = {};

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.new.newpar.section.aem-Grid-newComponent').forEach(element => {
      element.style.display = 'none';
         });

    const form = document.querySelector('#contact-form');

    const nameInput = document.querySelector('#contact-name');
    const emailInput = document.querySelector('#contact-email');
    const messageInput = document.querySelector('#contact-message');

    const nameError = document.querySelector('#contact-name-error');
    const emailError = document.querySelector('#contact-email-error');
    const messageError = document.querySelector('#contact-message-error');

    const formStatus = document.querySelector('#form-status');

    if (!form) return;

    form.addEventListener('submit', (e) => {

        e.preventDefault();

        let error = {};

        if (!nameInput.value.trim()) {
            error.name = 'Please enter your name.';
        }

        if (!emailInput.value.trim()) {
            error.email = 'Please enter your email.';
        } else if (!emailInput.validity.valid) {
            error.email = 'Please enter a valid email address.';
        }

        if (!messageInput.value.trim()) {
            error.message = 'Please enter your message.';
        }

        nameError.textContent = error.name || '';
        emailError.textContent = error.email || '';
        messageError.textContent = error.message || '';

        if (Object.keys(error).length > 0) {
            formStatus.textContent = 'Please correct the errors.';
            return;
        }

        const contactFormData = {
            name: nameInput.value.trim(),
            email: emailInput.value.trim(),
            message: messageInput.value.trim()
        };

        console.log(contactFormData);

        openModal(MODAL_CONTENT.contactSuccess);

        form.reset();

        setTimeout(() => {
            formStatus.textContent = '';
        }, 3000);

    });

});
