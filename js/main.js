var TuxRun = TuxRun || {};

TuxRun.game = new Phaser.Game(600, 320, Phaser.AUTO, '');

TuxRun.game.state.add('Boot', TuxRun.Boot);
TuxRun.game.state.add('Preload', TuxRun.Preload);
TuxRun.game.state.add('Game', TuxRun.Game);
TuxRun.game.state.add('GameMenu', TuxRun.GameMenu);
TuxRun.game.state.add('GameWon', TuxRun.GameWon);
TuxRun.game.state.start('Boot');
