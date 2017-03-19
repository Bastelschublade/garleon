var TuxRun = TuxRun || {}; //create or use existing Object
TuxRun.GameWon = function () {};

TuxRun.GameWon.prototype = {
    preload: function () {
      
    },
    create: function () {
        console.log('finish');
        this.background = this.add.tileSprite(0,0, 600, 320, 'background');
        this.finalText = this.game.add.text(220,150, 'You Won!');
    },
    update: function () {
    }
};