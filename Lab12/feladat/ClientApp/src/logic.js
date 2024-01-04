export class Logic {
    constructor(game) { this.game = game; }

    startGame() {
        const secretNumber = Math.floor(((Math.random() * 100) + 1));
        this.secretGuess = (num) => secretNumber < num ? 'less' : secretNumber > num ? 'greater' : 'correct';
    }

    guess(num) {
        if (this.secretGuess && typeof (this.secretGuess) === 'function') {
            setTimeout(() => this.game.onGuessed(num, this.secretGuess(num)), 400);
        }
    }
}