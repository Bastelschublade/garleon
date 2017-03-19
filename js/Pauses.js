var TuxRun = TuxRun || {}; //create or use existing Object

TuxRun.GameOver = function () {};
// general settings and preload loading screen
TuxRun.GameOver.prototype = { //prototype object created
    preload: function () {
    },
    create: function () {
    }
};


TuxRun.LevelDone = function () {}; //not that sure just creating subobject/ function?
// general settings and preload loading screen
//more intuitiv would be like
//TuxRun.LevelDone.prototype.preload = function(){ preload content...}
// -- || -- .prototype.create = function(){...} should be the same as this way
TuxRun.LevelDone.prototype = { //prototype object created
    preload: function () {
    },
    create: function () {
        
    }
};

TuxRun.GameDone = function () {};
// general settings and preload loading screen
TuxRun.GameDone.prototype = { //prototype object created
    preload: function () {
    },
    create: function () {
    }
};