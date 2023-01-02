class NwclibButton1 extends HTMLElement {

    constructor() {
        super();
        this.name = 'World';
    }

    connectedCallback() {

        // create a Shadow DOM
        const shadow = this.attachShadow({mode: 'closed'});

        // add elements to the Shadow DOM
        shadow.innerHTML = `
            <style>
              button {
                text-align: center;
                font-weight: bold;
                padding: 1em;
                margin: 0 0 2em 0;
                background-color: forestgreen;
                border: none;
              }
            </style>
            <button>Hello ${this.name}!</button>`;

    }

    static get observedAttributes() {
        return ['name'];
    }

    attributeChangedCallback(property, oldValue, newValue) {

        if (oldValue === newValue) return;
        this[property] = newValue;

    }

}

customElements.define('nwclib-button1', NwclibButton1);