/**
 * Settings
 */
document.addEventListener('DOMContentLoaded', () => {
    /**
     * Toggle Switches
     *
     * @type {NodeListOf<Element>} all toggle switches
     */
    const toggleSwitches = document.querySelectorAll('.toggle-switch');

    /**
     * For each toggle switch, add an event listener to toggle the switch
     */
    toggleSwitches.forEach(async toggle => {
        const settingName = toggle.dataset.setting;
        const savedState = await fetch(`/api/settings/get`, {
            method: 'GET'
        }).then(
            response => response.json()
        );

        const toggleName = toggle.id.replace("Toggle", "");

        for(const key in savedState) {
            if(key === toggleName) {
                const value = savedState[key].enabled;

                if(value === "true") {
                    toggle.classList.add('active');
                } else {
                    toggle.classList.remove('active');
                }
                break;
            }
        }

        /**
         * Toggle the switch
         */
        toggle.addEventListener('click', async _event => {
            toggle.classList.toggle('active');
            const isActive = toggle.classList.contains('active');

            await fetch('/api/settings/set', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({[settingName]: isActive})
            });
        });
    });

    const settingLabels = document.querySelectorAll('.setting-label');

    /**
     * For each setting label, add an event listener to toggle the switch
     */
    settingLabels.forEach(label => {
        label.addEventListener('click', _event => {
            const toggleId = label.getAttribute('for');
            const toggle = document.getElementById(toggleId);

            if(toggle)
                toggle.click();
        });
    });
});
