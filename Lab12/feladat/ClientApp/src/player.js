export class Player {
    constructor() {
        this.onNameSet = new Promise((resolve, reject) => {
            document.getElementById('start-form').addEventListener('submit', e => {
                e.preventDefault();
                const name = document.getElementById('name-input').value;
                if (name && name.length) {
                    resolve(name);
                }
            });
        });

        this.onNameSet.then(name => {
            this.name = name;
            this.render();
        });
    }

    render() {
        for (let element of [document.getElementById('name-input'), document.getElementById('start-button')])
            element.disabled = !!(this.name && this.name.length);
    }
}