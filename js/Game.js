var TuxRun = TuxRun || {}; //create or use existing Object
TuxRun.Game = function () {};
TuxRun.Game.prototype = {
    init: function (lvl, levelDeath){
        this.level = lvl;
        this.levelName = 'level'+lvl;
        this.levelFile = 'level'+lvl+'.json';
        this.levelDeath = levelDeath;
        
    },
    preload: function () {
        this.game.time.advancedTiming = true;
        this.load.tilemap(this.levelName, 'assets/level/'+this.levelFile, null, Phaser.Tilemap.TILED_JSON);
    },
    
    create: function () {
        //variablen
        //background.height = this.game.height;
        
        //backgound
        console.log('test2');
        this.background = this.game.add.tileSprite(0,0, 10,320, 'background');
        //load json data to game objects 
        
        this.map = this.game.add.tilemap(this.levelName);
        console.log('test3');
        //first para tileset name from Tiled secend the link to the image from preload
        this.map.addTilesetImage('tiles_sprite', 'gameTiles');
        this.backgroundlayer = this.map.createLayer('backgroundLayer');
        this.blockedLayer = this.map.createLayer('blockedLayer');
        this.blockedLayer = this.map.createLayer('blockedLayer');
        this.map.setCollisionBetween(1, 5000, true, 'blockedLayer'); //for all tile ids (10x50)
        this.backgroundlayer.resizeWorld();
        this.background.width = this.game.world.width;
        this.createCoins();
        //physics for player
        this.player = this.game.add.sprite(20,300,'playerWalk');
        var walk = this.player.animations.add('walk');
        this.player.animations.play('walk', 20, true);
        this.game.physics.arcade.enable(this.player);
        this.player.body.gravity.y = 2000;
        //slideplayer settings
        var playerSlideImg = this.game.cache.getImage('playerSlide');
        //set hitboxes
        this.player.slidedDimensions = {width: this.player.width, height: 32};
        this.player.walkDimensions = {width: this.player.width, height: this.player.height};
        this.player.anchor.setTo(0.5,1); //anchor hitbox to center of player
        this.game.camera.follow(this.player);
        this.cursors = this.game.input.keyboard.createCursorKeys();
        if('ontouchstart' in document.documentElement){
            this.initGameController();
        }
        this.player.score = 0;
        //this.coinSound = this.game.add.audio('coin');
    },
     //find objects in a Tiled layer that containt a property called "type" equal to a certain value
    findObjectsByType: function(type, map, layerName) {
        var result = new Array();
        map.objects[layerName].forEach(function(element){
          if(element.properties.type === type) {
            //Phaser uses top left, Tiled bottom left so we have to adjust
            //also keep in mind that some images could be of different size as the tile size
            //so they might not be placed in the exact position as in Tiled
            element.y -= map.tileHeight;
            result.push(element);
          }      
        });
        return result;
      },
      //create a sprite from an object
      createFromTiledObject: function(element, group) {
        var sprite = group.create(element.x, element.y, element.properties.sprite);

          //copy all properties to the sprite
          Object.keys(element.properties).forEach(function(key){
            sprite[key] = element.properties[key];
          });
    },
    update: function () {
        console.log(this.levelDeath);
        //move background
        this.background.x = this.game.camera.x*0.5;
        //collision
        this.game.physics.arcade.collide(this.player, this.blockedLayer, this.playerHit, null, this);
        this.game.physics.arcade.overlap(this.player, this.coins, this.collect, null, this);
        
        //only respond to keys and keep the speed if the player is alive
        if(this.player.alive) {
          this.player.body.velocity.x = 300;  

          if(this.cursors.up.isDown) {
            this.playerJump();
          }
          else if(this.cursors.down.isDown) {
            this.playerSlide();
          }

          if(!this.cursors.down.isDown && this.player.isSlided && !this.pressingDown) {
            //change image and update the body size for the physics engine
            this.player.loadTexture('playerWalk');
            this.player.animations.play('walk', 20, true);
            this.player.body.setSize(this.player.walkDimensions.width, this.player.walkDimensions.height);
            this.player.isSlided = false;
          }
            
            //kill player bottom (does not trigger actually)
            
            if(this.player.y >= this.game.world.height){
                console.log('felt down');

                this.player.body.velocity.x = 0;
                this.player.loadTexture('playerDead');
                if(this.player.alive){
                    this.game.time.events.add(100,this.die, this);
                }
                this.player.alive = false;
                this.game.time.events.add(1500, this.gameOver, this);
            }
            
          //start next level if player hits the end
            //todo show statistic
          if(this.player.x >= this.game.world.width-20) {
                //this.game.paused = true;
                this.player.body.velocity.x = 0;
                this.player.body.velocity.y = 0;
                this.player.loadTexture('player');
                this.game.time.events.add(100, this.levelPopup, this);
                this.game.time.events.add(2000, this.levelDone, this);
          }
        }
        if (!this.player.alive){
            
        }
    },
    playerHit: function (player, blockedLayer) {
        if(player.body.blocked.right) {
            //set dead
            this.player.loadTexture('playerDead');
            this.player.body.velocity.x = 0;
            
            console.log('crashed');
            if(this.player.alive){
                this.game.time.events.add(300,this.die, this);
            }
            this.player.alive = false;
            this.game.time.events.add(2000, this.gameOver, this);
            
        }
        if(player.body.blocked.down && this.player.isJumped && this.player.alive) {
            this.player.isJumped = false;
            this.player.loadTexture('playerWalk');
            this.player.animations.play('walk', 20, true);
        }
    },

    collect: function(player, collectable) {
    //play audio
    //this.coinSound.play();

    //remove sprite
        collectable.destroy();
        this.player.score += 1;
  },
  initGameController: function() {

    if(!GameController.hasInitiated) {
      var that = this;
      
      GameController.init({
          left: {
              type: 'none',
          },
          right: {
              type: 'buttons',
              buttons: [
                false,
                {
                  label: 'J', 
                  touchStart: function() {
                    if(!that.player.alive) {
                      return;
                    }
                    that.playerJump();
                  }
                },
                false,
                {
                  label: 'D',
                  touchStart: function() {
                    if(!that.player.alive) {
                      return;
                    }
                    that.pressingDown = true; that.playerSlide();
                  },
                  touchEnd: function(){
                    that.pressingDown = false;
                  }
                }
              ]
          },
      });
      GameController.hasInitiated = true;
    }

  },
  //create coins
  createCoins: function() {
    this.coins = this.game.add.group();
    this.coins.enableBody = true;
    var result = this.findObjectsByType('coin', this.map, 'objectsLayer');
      this.maxcoins = 0;
    result.forEach(function(element){
      this.createFromTiledObject(element, this.coins);
        this.maxcoins +=1;
    }, this);
  },
    die: function(){
        this.player.alive = false;
        this.levelDeath += 1;
        this.player.loadTexture('playerDead');
        this.popup = this.game.add.sprite(this.game.camera.x+105,50, 'pauseBg');
        this.txtDead = this.game.add.text(this.game.camera.x+230, 92, 'You FAILED!', {font: 'bold 12pt TheMinion', fill: '#840'});
        this.txtDeath = ''+this.levelDeath;
        this.scoreString = this.player.score + ' / '+this.maxcoins;
        this.picScore = this.game.add.sprite(this.game.camera.x+170, 145, 'goldCoin');
        this.txtScore = this.game.add.text(this.game.camera.x+200, 150, this.scoreString, {font: 'bold 13pt TheMinion', fill: '#dd9'});
        this.picDead = this.game.add.sprite(this.game.camera.x+340, 145, 'tuxSkull');
        this.txtScore = this.game.add.text(this.game.camera.x+370, 150, this.txtDeath, {font: 'bold 13pt TheMinion', fill: '#dd9'});
    },
    
    gameOver: function() {
        this.game.state.start('Game', true, false, this.level, this.levelDeath);
    },
    levelPopup: function() {
        this.popup = this.game.add.sprite(this.game.camera.x+105,50, 'pauseBg');
            this.txtDead = this.game.add.text(this.game.camera.x+230, 92, 'Level Done!', {font: 'bold 12pt TheMinion', fill: '#840'});
            this.txtDeath = ''+this.levelDeath;
            this.scoreString = this.player.score + ' / '+this.maxcoins;
            this.picScore = this.game.add.sprite(this.game.camera.x+170, 145, 'goldCoin');
            this.txtScore = this.game.add.text(this.game.camera.x+200, 150, this.scoreString, {font: 'bold 13pt TheMinion', fill: '#dd9'});
            this.picDead = this.game.add.sprite(this.game.camera.x+340, 145, 'tuxSkull');
            this.txtScorse = this.game.add.text(this.game.camera.x+370, 150, this.txtDeath, {font: 'bold 13pt TheMinion', fill: '#dd9'});
    },
    levelDone: function() {
        if(this.level < 2){
            this.game.state.start('Game', true, false, this.level+1, 0);
            
        }
        else{
            this.game.state.start('GameWon', true, false);
        }
        this.levelDeath = 0;
    },
    playerJump: function() {
        if(this.player.body.blocked.down) {
          this.player.body.velocity.y -= 700;
            this.player.loadTexture('playerJump');
            this.player.isJumped = true;
            
        
        }    
    },
    playerSlide: function() {
      //change image and update the body size for the physics engine
      this.player.loadTexture('playerSlide');
      this.player.body.setSize(this.player.slidedDimensions.width, this.player.slidedDimensions.height);
      
      //we use this to keep track whether it's sliding or not
      this.player.isSlided = true;
    },
    render: function()
    {
        this.game.debug.text(this.game.time.fps || '--', 20, 70, "#00ff00", "10px Courier");
        this.game.debug.text(this.player.y || '--', 20, 90, "#fff", "10px Courier");
        this.game.debug.text(this.player.score || '--', 20, 100, "#fff", "10px Courier");
        this.game.debug.text(this.maxcoins || '--', 20, 110, "#fff", "10px Courier");
    }
    //render funcion for debug

}