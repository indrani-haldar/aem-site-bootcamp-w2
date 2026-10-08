document.addEventListener('DOMContentLoaded', () => {
    if (!window.location.pathname.includes('/contact')) {
        return;
    }

    document.querySelectorAll('form.cmp-form').forEach((form) => {
        form.addEventListener('submit', (event) => {
            event.preventDefault();
            if (!form.reportValidity()) {
                return;
            }

            let status = form.querySelector('[data-shopfast-form-status]');
            if (!status) {
                status = document.createElement('p');
                status.dataset.shopfastFormStatus = 'true';
                status.setAttribute('role', 'status');
                status.setAttribute('aria-live', 'polite');
                form.prepend(status);
            }

            status.textContent = 'Your message was successfully submitted.';
            form.reset();
        });

        form.querySelectorAll('.cmp-form-text').forEach((field) => {
            const input = field.querySelector('input, textarea');
            const labels = { name: 'Name', email: 'Email', message: 'Message' };
            const label = field.querySelector('label');
            if (input && label && labels[input.name]) {
                label.textContent = labels[input.name];
            }
        });

        const submit = form.querySelector('.cmp-form-button');
        if (submit) {
            submit.textContent = 'Send Message';
        }
    });
});
