export class Toplist {
    constructor() {
        this.items = JSON.parse(localStorage.getItem('toplist') || '[]');
        this.render();
    }

    setItem(name, guesses, time) {
        this.items.push({ name, guesses, time });
        this.items = this.items.sort((a, b) => a.guesses + a.time / 1000 - (b.guesses + b.time / 1000)).slice(0, 9);
        localStorage.setItem('toplist', JSON.stringify(this.items));
        this.render();
    }

    render() {
        document.querySelector('#toplist tbody').innerHTML = this.items.map((e, i) => (
            `<tr>
                <th>${i + 1}</th>
                <td>${e.name}</td>
                <td>${e.guesses}</td>
                <td>${e.time}</td>
            </tr>`
        )).join('');
    }
}