export class Guess {
    constructor(game) {
        document.getElementById('guess-form').addEventListener('submit', e => {
            e.preventDefault();
            const value = parseInt(document.getElementById('guess-input').value);
            if (!isNaN(value) && value > 0 && value <= 100)
                game.onGuessing(value);
            document.getElementById('guess-form').reset();
        });
        document.getElementById('guess-form').reset();
        this.setEnabled(false);
    }

    setEnabled(value) {
        this.enabled = !!value;
        this.render();
    }

    render() {
        for (let element of [document.getElementById('guess-input'), document.getElementById('guess-button')]) {
            element.disabled = !this.enabled;
        }

        if (!this.enabled) {
            document.getElementById('guess-input').focus();
        }
    }
}