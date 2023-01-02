class NwclibButton2 extends HTMLElement {

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
                padding: 2em;
                margin: 0 0 2em 0;
                background-color: cornflowerblue;
                border: solid 1px black;
                border-radius: 5px;
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

customElements.define('nwclib-button2', NwclibButton2);