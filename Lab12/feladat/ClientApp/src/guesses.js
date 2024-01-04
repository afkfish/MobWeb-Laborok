export class Guesses {
    constructor() {
        this.clear();
    }

    addGuess(num, value) {
        if (num && value) {
            this.guesses.push({ num, value });
            this.render();
        }
    }

    clear() {
        this.guesses = [];
        this.render();
    }

    render() {
        for (let tr of Array.from(document.querySelectorAll('#guesses tbody tr')))
            tr.remove();

        for (let tr of (this.guesses.map((g, i) => 
                `<tr>
                    <td>${i + 1}</td>
                    <td class='text-right'>${g.num}</td>
                    <td class='bg-${g.value === 'correct' ? 'success' : 'danger'}'>${g.value === 'correct' ? '!!!' : g.value === 'less' ? '&gt;' : '&lt;'}</td>
                </tr>`).reverse()))
            document.querySelector('#guesses tbody').innerHTML += tr;
    }
}