var TuxRun = TuxRun || {}; //create or use existing Object
TuxRun.GameMenu = function () {};

TuxRun.GameMenu.prototype = {
    preload: function () {
      
    },
    create: function () {
        console.log('menu '+this.game.levelDeath)
        this.background = this.add.tileSprite(0,0, 600, 320, 'background');
        // define "create_button(text)" function?
        this.button1 = this.add.sprite(210,43, 'button');
        this.button2 = this.add.sprite(210, 123, 'button');
        this.button3 = this.add.sprite(210, 203, 'button');
        // looks like we have to create a style for or menu option
        var optionStyle = { font: '24pt TheMinion', fill: '#FEFFD5', align: 'center'};
        // the text for start
        this.txtPlay = this.game.add.text(235, 55, 'play', optionStyle);
        var txtLevel = this.game.add.text(230, 135, 'level', optionStyle);
        var txtInfo = this.game.add.text(239, 215, 'info', optionStyle);
        // so how do we make it clickable?  We have to use .inputEnabled!
        this.txtPlay.inputEnabled = true;
        txtLevel.inputEnabled = true;
        txtInfo.inputEnabled = true;
        // Now every time we click on it, it says "You did it!" in the console!
        this.txtPlay.events.onInputOver.add(function (target) {
            target.fill = "white";
            target.textDecoration = "underline";
            
        });
        this.txtPlay.events.onInputOut.add(function (target) {
            target.fill = "#FEFFD5";
            target.textDecoration = "none";
        });
        this.txtPlay.events.onInputUp.add(function () { 
            //start game with paramters (resetworld, resetcache, level)
            TuxRun.game.state.start('Game', true, false, 0, 0);
            });
        txtLevel.events.onInputUp.add(function () { console.log('Level Pressed') });
        txtInfo.events.onInputUp.add(function () { console.log('Info Pressed')});

        
    }
};