import { Player } from './player.js';

export class Restart {
    constructor(game) {
        this.game = game;
    }

    restart() {
        document.getElementById('restart-div').remove();
        document.getElementById('start-form').reset();
        this.game.player = new Player();
        this.game.timer.clear();
        this.game.guesses.clear();
    }
}