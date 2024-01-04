export class Timer {
    start() {
        this.started = Date.now();
        this.interval = setInterval(() => this.render(), 150);
    }

    stop() {
        this.stopped = Date.now();
        clearInterval(this.interval);
    }

    clear() {
        this.started = this.stopped = undefined;
    }

    getElapsedTime() {
        return this.started ? Math.floor(((this.stopped ? this.stopped : Date.now()) - this.started) / 1000) : "-";
    }

    render() {
        document.getElementById("timer").innerText = this.getElapsedTime();
    }
}