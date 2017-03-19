var TuxRun = TuxRun || {}; //create or use existing Object
TuxRun.Boot = function () {}; //not that sure just creating subobject/ function?

// general settings and preload loading screen
TuxRun.Boot.prototype = { //prototype object created
    preload: function () {
        this.load.image('bootImage', 'assets/img/tux_logo.png');
    },
    create: function () {
        //create white background for loading screen
        this.game.stage.backgroundColor = '#fff';
        //keep ratio max until x/y hit border, and center x/y
        this.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
        this.scale.pageAlignHorizontally = true;
        this.scale.pageAlignVertically = true;
        //set screensize automatic
        this.scale.setScreenSize(true);
        this.game.physics.startSystem(Phaser.Physics.ARCADE);
        this.state.start('Preload');
    }
};