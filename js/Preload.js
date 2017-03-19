var TuxRun = TuxRun || {}; //create or use existing Object
TuxRun.Preload = function () {};
TuxRun.Preload.prototype = {
    preload: function () {
        //show loadscreen
        this.bootImage = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'bootImage');
        //this.bootImage.anchor.setTo(0.5); //sets anchor to middle not left?
        //this.bootImage.scale.setTo(3);
        //this.add.Sprite(200, 200, this.bootImage);
        
        //load game Assets
        //this.load.tilemap('level1', 'assets/level/level1.json', null, Phaser.Tilemap.TILED_JSON);
        this.maxlevel = 2;
        this.load.image('pauseBg', 'assets/img/pause_background.png');
        this.load.image('gameTiles', 'assets/img/tiles_sprite.png');
        this.load.image('player', 'assets/img/tux.png');
        this.load.image('playerSlide', 'assets/img/tux_slide.png');
        this.load.image('playerDead', 'assets/img/tux_dead.png');
        this.load.image('playerJump', 'assets/img/tux_jump.png');
        this.load.image('goldCoin', 'assets/img/coin_gold.png');
        this.load.image('tuxSkull', 'assets/img/tux_skull.png');
        this.load.image('background', 'assets/img/background.png');
        this.load.image('button', 'assets/img/button.png');
        this.load.spritesheet('playerWalk', 'assets/img/tux_walk_sprite.png', 32, 42, 9);
        //this.load.audio('coin', ['assets/audio/coin.ogg', 'assets/audio/coin.mp3']);
    },
    create: function () {
        //start Game.js after loading finished
        this.state.start('GameMenu');
    }
}