const {app, BrowserWindow} = require('electron')

let win;

function createWindow() {
    win = new BrowserWindow({
        width: 700,
        height: 500
    })


    win.loadURL('http://localhost:8080/main')
}


app.on('ready', createWindow)