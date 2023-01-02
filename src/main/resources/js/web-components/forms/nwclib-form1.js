class NwclibForm1 extends HTMLElement {

    constructor() {
        super();
    }

    connectedCallback() {
        let shadowRoot = this.attachShadow({mode: 'open'});
        shadowRoot.innerHTML = this.template;
    }

    get template() {
        return `
        <form>
            <label for="fname">First name:</label><br>
            <input type="text" id="fname" name="fname" value="John"><br>
            <label for="lname">Last name:</label><br>
            <input type="text" id="lname" name="lname" value="Doe"><br><br>
            <input type="submit" value="Submit">
        </form>`;
    }
}

window.customElements.define('nwclib-form1', NwclibForm1);
