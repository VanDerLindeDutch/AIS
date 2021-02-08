const {app, BrowserWindow} = require('electron')

let win;

function createWindow() {
    win = new BrowserWindow({
        width: 1400,
        height: 900
    })


    win.loadURL('http://localhost:8080/start')
}


app.on('ready', createWindow)