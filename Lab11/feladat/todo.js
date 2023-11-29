class Todo {
    constructor(name, state) {
        this.name = name;
        this.state = state;
    }
}

class Button {
    constructor(action, icon, type, title) {
        this.action = action;
        this.icon = icon;
        this.type = type;
        this.title = title;
    }
}

const todos = [];   // Az oldal betöltődésekor a TODO lista egy üres tömb.
const states = ["active", "inactive", "done"];
const tabs = ["all"].concat(states);    // "all" + a state tömb elemei a fülek.
const buttons = [];
buttons.push(new Button("done", "check", "success", "Mark as done"));
buttons.push(new Button("active", "plus", "secondary", "Mark as active"));
buttons.push(new Button("inactive", "minus", "secondary", "Mark as inactive"));
buttons.push(new Button("remove", "trash", "danger", "Remove"));

const arrowButtons = [];
arrowButtons.push(new Button("up", "arrow-up", "secondary", "Move up"));
arrowButtons.push(new Button("down", "arrow-down", "secondary", "Move down"));


const todoForm = document.getElementById("new-todo-form");
const input = document.getElementById("new-todo-title");

const saveTodos = () => {
    localStorage.setItem("todos", JSON.stringify(todos));
};

const loadTodos = (() => {
    const pers = JSON.parse(localStorage.getItem("todos"));
    if (pers) {
        pers.forEach(todo => {
            todos.push(new Todo(
                todo.name,
                todo.state
            ));
        });
    }
})();

todoForm.onsubmit = (event) => {
    event.preventDefault();

    if (input?.value === "") {
        return;
    }

    const todo = new Todo(
        input.value,
        states[0]
    );
    todos.push(todo);
    input.value = "";

    saveTodos();

    renderTodoList();
};

const createElementFromHTML = (html) => {
    const virtualElement = document.createElement("div");
    virtualElement.innerHTML = html;

    return virtualElement.childElementCount == 1 
        ? virtualElement.firstChild 
        : virtualElement.children;
};


const renderTodoList = () => {
    const todoList = document.getElementById("todo-list");
    todoList.innerHTML = "";

    todos
    .filter(todo => ["all", todo.state].includes(currentTab))
    .forEach(todo => {
        const row = createElementFromHTML(
            `<div class="row">
                <div class="col d-flex p-0">
                    <a class="list-group-item flex-grow-1" href="#">
                        ${todo.name}
                    </a>
                    <div class="btn-group action-buttons"></div>
                </div>
            </div>`);

        buttons.forEach(button => {
            const btn = createElementFromHTML(
                `<button class="btn btn-outline-${button.type} fas fa-${button.icon}" title="${button.title}"></button>`
            );

            btn.disabled = todo.state === button.action;

            btn.onclick = () => {
                if (button.action === "remove") { 
                    if (confirm("Are you sure you want to delete the todo titled '" + todo.name + "'?")) { 
                        todos.pop(todo);

                        saveTodos();

                        renderTodoList();
                    } 
                } else {
                    todo.state = button.action;
                    renderTodoList();
                }
            };

            row.querySelector(".action-buttons").appendChild(btn); 
        });

        if (currentTab === "all") {
            arrowButtons.forEach(button => {
                const btn = createElementFromHTML(
                    `<button class="btn btn-outline-${button.type} fas fa-${button.icon}" title="${button.title}"></button>`
                );

                btn.onclick = () => {
                    const index = todos.indexOf(todo);
                    const newIndex = button.action === "up" ? index - 1 : index + 1;
                    todos.splice(newIndex, 0, todos.splice(index, 1)[0]);

                    saveTodos();

                    renderTodoList();
                };

                if (button.action === "up" && todo === todos[0]) {
                    btn.disabled = true;
                }

                if (button.action === "down" && todo === todos[todos.length - 1]) {
                    btn.disabled = true;
                }

                row.querySelector(".action-buttons").appendChild(btn); 
            });
        }

        todoList.appendChild(row);
    });

    document.querySelector(".todo-tab[data-tab-name='all'] .badge").innerHTML = todos.length || "";
    states.forEach(state => {
        document.querySelector(`.todo-tab[data-tab-name='${state}'] .badge`).innerHTML = todos.filter(todo => todo.state === state).length || "";
    });
}

let currentTab = "all";

const selectTab = (type) => {
    currentTab = type;

    for (let tab of document.getElementsByClassName("todo-tab")) {
        tab.classList.remove("active");

        if (tab.getAttribute("data-tab-name") == type) {
            tab.classList.add("active");
        }
    }

    renderTodoList();
};

selectTab(currentTab);